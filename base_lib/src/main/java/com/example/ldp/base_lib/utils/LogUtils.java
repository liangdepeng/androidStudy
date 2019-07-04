package com.example.ldp.base_lib.utils;

import android.util.Log;

/**
 * created by Da Peng at 2019/6/24
 */
public class LogUtils {

    private static boolean isDebug = false;

    public static void e(String tag,String message){
        if (isDebug){
            Log.e(tag,message);
        }
    }

    public static void w(String tag,String message){
        if (isDebug){
            Log.w(tag,message);
        }
    }

    public static void i(String tag,String message){
        if (isDebug){
            Log.i(tag,message);
        }
    }

    public static void d(String tag,String message){
        if (isDebug){
            Log.d(tag,message);
        }
    }

    public static void v(String tag,String message){
        if (isDebug){
            Log.v(tag,message);
        }
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }
}
