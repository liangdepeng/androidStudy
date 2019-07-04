package com.example.ldp.base_lib.utils;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Looper;

/**
 * created by Da Peng at 2019/6/21
 */
public class AppUtils {

    public static boolean isMainThread = false;

    public static String stringRemoveSpace(Object str) {
        return str.toString().trim();
    }

    /**
     * 判断当前线程是否是主线程
     *
     * @return
     */
    public static String getThreadInfo() {
        isMainThread = false;

        //方法1
        if (Looper.getMainLooper() == Looper.myLooper()) {
            isMainThread = true;
        }

        //方法2
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            isMainThread = true;
        }

        //方法3
        if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
            isMainThread = true;
        }

        String str = " 【threadName : "
                + Thread.currentThread().getName() + "】-【threadId : "
                + Thread.currentThread().getId() + "】-【threadState : "
                + Thread.currentThread().getState();

        if (isMainThread) {
            return str + "】--- 主线程";
        } else {
            return str + "】--- 子线程";
        }

    }

    /**
     * 判断是否是网络的资源
     *
     * @param uri
     * @return
     */
    public boolean isNetUri(String uri) {
        boolean reault = false;
        if (uri != null) {
            if (uri.toLowerCase().startsWith("http") || uri.toLowerCase().startsWith("rtsp") || uri.toLowerCase().startsWith("mms")) {
                reault = true;
            }
        }
        return reault;
    }


    /**
     * 得到网络速度
     * 每隔两秒调用一次
     *
     * @param context
     * @return
     */

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public String getNetSpeed(Context context) {
        String netSpeed = "0 kb/s";
        //转为KB
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) ==
                TrafficStats.UNSUPPORTED ? 0 :
                (TrafficStats.getTotalRxBytes() / 1024);
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        netSpeed = String.valueOf(speed) + " kb/s";
        return netSpeed;
    }
}
