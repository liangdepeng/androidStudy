package com.example.catchcrashlib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TipActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        final TextView textView = findViewById(R.id.crashInfo);
        if (null != getIntent()) {
            final String content = getIntent().getStringExtra("context");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText(!TextUtils.isEmpty(content) ? content : "无详细信息");
                    //showCrashInfoDialog(!TextUtils.isEmpty(content) ? content : "无详细信息");
                }
            }, 100);
        }
    }

    public static void startTipActivity(Context appContext,String crashInfo) {
        if (appContext == null) return;
        Activity currentActivity = AppManager.getInstance().getCurrentActivity();
        Intent intent = null;
        if (currentActivity == null) {
            intent = new Intent(MyTestApplication.getAppContext(), TipActivity.class);
            intent.putExtra("context", crashInfo);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            appContext.startActivity(intent);
        } else {
            intent = new Intent(AppManager.getInstance().getCurrentActivity(), TipActivity.class);
            intent.putExtra("context", crashInfo);
            AppManager.getInstance().getCurrentActivity().startActivity(intent);
        }

    }

    private void showCrashInfoDialog(String stringBuilder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppManager.getInstance().getCurrentActivity());
        AlertDialog dialog = builder.setTitle("错误信息")
                .setMessage(stringBuilder.toString())
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
