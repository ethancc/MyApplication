package com.ethan.xlib.component;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.ethan.xlib.app.SharedPrefenceHelper;

/**
 *crash的本地缓存处理，当出现连续的crash时，连续出现3次则清理本地缓存及统计上报等工作
 *
 *
 * Created by ethamhuang on 2015/10/30.
 */
public class FrequentlyCrashProtecter implements Handler.Callback{

    private static final String TAG = "ethan";

    private static volatile FrequentlyCrashProtecter sInstance = new FrequentlyCrashProtecter(null);

    private static final String KEY_CONTINUOUSLY_CRASH_COUNT = "continuouslyCrashCount";
    private static final int DEFAULT_FREQUENTLY_CRASH_COUNT = 3;
    private static final long DEFAULT_RESET_CRASH_COUNT_INTERVAL = 30 * 1000;//25秒
    private static final int WHAT_RESET_FREQUENTLY_CRASH_COUNT = 1;

    private volatile int mCrashCount = -1;

    private Handler mHandler;

    private Context context;

    public static FrequentlyCrashProtecter getInstance() {
        return sInstance;
    }

    private FrequentlyCrashProtecter(Context context) {
        mHandler = new Handler(Looper.getMainLooper(), this);
        mHandler.sendEmptyMessageDelayed(WHAT_RESET_FREQUENTLY_CRASH_COUNT, DEFAULT_RESET_CRASH_COUNT_INTERVAL);
        this.context = context;
    }


    /**
     * 当出现Uncaut Exception时，在{@link UncaughtExceptionHandler#uncaughtException(Thread, Throwable)}中调用此方法
     * @param e
     */
    public void onUncautException(Throwable e) {
        mHandler.removeMessages(WHAT_RESET_FREQUENTLY_CRASH_COUNT);
        addContinuouslyCrashCount();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 重置连续crash计数
     */
    private void resetContinuouslyCrashCount() {
        mCrashCount = 0;
        SharedPrefenceHelper.getDefaultSharedPref(context).edit().remove(KEY_CONTINUOUSLY_CRASH_COUNT).commit();
        mHandler.removeMessages(WHAT_RESET_FREQUENTLY_CRASH_COUNT);
    }

    /**
     * 连续crash次数加1
     */
    private void addContinuouslyCrashCount() {
        int crashCount = getCrashCount();
        mCrashCount = ++crashCount;
        Log.i(TAG, "addContinuouslyCrashCount -->" + crashCount);
        SharedPrefenceHelper.getDefaultSharedPref(context).edit().putInt(KEY_CONTINUOUSLY_CRASH_COUNT, crashCount).commit();
    }

    /**
     * 是否发生了频繁的crash
     */
    private boolean isFrequentlyCrash() {
        return getCrashCount() >= DEFAULT_FREQUENTLY_CRASH_COUNT;
    }

    private int getCrashCount() {
        int crashCount = mCrashCount;
        if (crashCount == -1) {
            crashCount =  SharedPrefenceHelper.getDefaultSharedPref(context).getInt(KEY_CONTINUOUSLY_CRASH_COUNT, 0);
            mCrashCount = crashCount;
        }
        return crashCount;
    }

    public void onAppStart(){
        if(isFrequentlyCrash()){
            Log.i(TAG, "clear feed cache by frequentlyCrash...");
            //CacheClearManager.clearDatabase(false);
            //CacheClearManager.clearImageCache();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_RESET_FREQUENTLY_CRASH_COUNT:
                Log.i(TAG, "reset frequently crash count...");
                resetContinuouslyCrashCount();
                break;
        }

        return false;
    }
}
