package com.example.ethan.utils;

import android.util.Log;

/**
 * Created by ethamhuang on 2015/8/28.
 */
public class ThreadLockTest {

    private static ThreadLockTest instance = null;
    private static byte[] lock = new byte[0];
    private int flag = 0;

    public static ThreadLockTest getInstance(){
        Log.i("ethan", "get instance by " + Thread.currentThread().getId());
        if(instance == null){
            Log.i("ethan", "get instance null by " + Thread.currentThread().getId());
            synchronized (lock){
                Log.i("ethan", "lock by " + Thread.currentThread().getId());
                if(instance == null){
                    instance = new ThreadLockTest();
                }
            }
            Log.i("ethan", "release by " + Thread.currentThread().getId());
        }

        return instance;
    }

    private ThreadLockTest(){
        Log.i("ethan", "<INIT>  start " + Thread.currentThread().getId() + ", flag " + flag);
        if(flag == 0){
            flag = 1;
            try {
                Log.i("ethan", "begin to sleep " + Thread.currentThread().getId());
                Thread.sleep(50*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("ethan", "<INIT>  end " + Thread.currentThread().getId() + ", flag " + flag);
    }


}
