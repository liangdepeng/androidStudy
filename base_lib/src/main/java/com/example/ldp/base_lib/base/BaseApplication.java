package com.example.ldp.base_lib.base;

import android.app.Application;
import android.content.Context;

/**
 * created by Da Peng at 2019/6/26
 */
public abstract class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getAppContent(){
        return context;
    }

}
