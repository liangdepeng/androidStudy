package com.example.ldp.base_lib.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

public class AppManager {

    private final Stack<Activity> activityStack = new Stack<>();

    private AppManager() {
    }

    private static class Manager {
        private static final AppManager INSTANCE = new AppManager();
    }

    public static AppManager getInstance() {
        return Manager.INSTANCE;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public Activity getCurrentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        } else {
            return activityStack.lastElement();
        }
    }

    public void finishCurrentActivity() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null)
                activity.finish();
        }
        activityStack.clear();
    }

    public void exit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                activityManager.killBackgroundProcesses(context.getPackageName());
            }
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
