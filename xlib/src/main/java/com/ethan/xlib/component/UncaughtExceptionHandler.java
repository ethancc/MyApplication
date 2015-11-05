package com.ethan.xlib.component;

import android.util.Log;

/**
 * Created by ethamhuang on 2015/10/26.
 */
public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    private static UncaughtExceptionHandler instance = new UncaughtExceptionHandler();

    public static UncaughtExceptionHandler getInstance(){
        return instance;
    }


    public void register(){
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        Log.d("ethan", "uncaughtException id:" + thread.getId() + ", ex:" + ex);

    }
}
