package com.example.catchcrashlib;

import android.app.Application;
import android.content.Context;

/**
 * @author Administrator
 */
public class MyTestApplication extends Application {

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        ExceptionCrashHandler.getInstance().initInterceptCrash(applicationContext);
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
