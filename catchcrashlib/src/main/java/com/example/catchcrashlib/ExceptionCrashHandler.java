package com.example.catchcrashlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常 拦截  防止崩溃
 *
 * @author 14515
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private Context appContext;
    private final HashMap<String, String> crashInfoMap = new HashMap<>();


    private ExceptionCrashHandler() {

    }

    private static class CashHandler {
        private static final ExceptionCrashHandler INSTANCE = new ExceptionCrashHandler();
    }

    public static ExceptionCrashHandler getInstance() {
        return CashHandler.INSTANCE;
    }

    /**
     * @param context getApplicationContext
     */
    public void initInterceptCrash(Context context) {
        this.appContext = context;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable throwable) {
                        tryCatchCrash(throwable);
                    }
                }
            }
        });
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        tryCatchCrash(e);
    }

    private void tryCatchCrash(@NonNull Throwable e) {
        PackageManager packageManager = appContext.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(appContext.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (packageInfo != null) {
                crashInfoMap.clear();
                String versionName = TextUtils.isEmpty(packageInfo.versionName) ? " not set versionName " : packageInfo.versionName;
                String versionCode = TextUtils.isEmpty(String.valueOf(packageInfo.versionCode)) ? "not set versionCode" : String.valueOf(packageInfo.versionCode);
                crashInfoMap.put("versionName 版本名称", versionName);
                crashInfoMap.put("versionCode 版本号：", versionCode);

                Field[] fields = Build.class.getFields();
                if (fields != null && fields.length > 0) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        crashInfoMap.put(field.getName(), field.get(null).toString());
                    }
                }

                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : crashInfoMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    stringBuilder.append(key).append(" = ").append(value).append("\n\n");
                }

                stringBuilder.append("\n===========Crash  Log  ↓↓↓↓↓↓↓↓↓↓↓↓  Begin============\n");
                String crashTime = DateUtil.getNowTime();
                stringBuilder.append(crashTime).append("\n");
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);

                e.printStackTrace();

                e.printStackTrace(printWriter);
                Throwable cause = e.getCause();
                while (cause != null) {
                    cause.printStackTrace(printWriter);
                    cause = e.getCause();
                }

                printWriter.close();


                String resultStr = stringWriter.toString();
                stringBuilder.append(resultStr);
                stringBuilder.append("\n===========Crash  Log  ↑↑↑↑↑↑↑↑↑↑↑  End==============\n");

                Log.e("crashLog", stringBuilder.toString());

                TipActivity.startTipActivity(appContext, stringBuilder.toString());
//                String fileName = "crash" + dateFormat + ".txt";
            }
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }


}
