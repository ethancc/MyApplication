package com.ethan.xlib.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by ethamhuang on 2016/5/4.
 */
public class TAppBase extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
