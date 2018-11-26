package com.yoie.com.clockapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class ActivityLifeCycleHelper implements Application.ActivityLifecycleCallbacks {

    private static ActivityLifeCycleHelper instance;

    private int numStarted = 0;
    private OnEnterForegroundListener onEnterForegroundListener;

    public static ActivityLifeCycleHelper init(Application application) {
        if (instance == null) {
            instance = new ActivityLifeCycleHelper();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public void setOnEnterForegroundListener(OnEnterForegroundListener onEnterForegroundListener) {
        this.onEnterForegroundListener = onEnterForegroundListener;
    }

    public ActivityLifeCycleHelper() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Log.d("LifeCycle","onActivityCreated");

    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d("LifeCycle","onActivityStarted");

        if (numStarted == 0) {
            // App 進入前景
            if (onEnterForegroundListener != null) {
                onEnterForegroundListener.onEnterForeground();
            }
        }
        numStarted++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d("LifeCycle","onActivityResumed");

    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d("LifeCycle","onActivityPaused");

    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d("LifeCycle","onActivityStopped");

        numStarted--;
        if (numStarted == 0) {
            // App  進入背景
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Log.d("LifeCycle","onActivitySaveInstanceState");

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d("LifeCycle","onActivityDestroyed");

    }

    public interface OnEnterForegroundListener {
        void onEnterForeground();
    }

    // 省略其他 interface 的 methods
}
