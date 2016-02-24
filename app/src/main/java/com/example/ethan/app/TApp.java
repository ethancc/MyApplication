package com.example.ethan.app;

import android.app.Application;
import android.app.Instrumentation;

import com.ethan.xlib.app.MyInstrumentation;
import com.ethan.xlib.common.util.ReflectHelper;

/**
 * Created by ethamhuang on 2016/1/27.
 */
public class TApp extends Application {

    private Object mActivityThreadObj = null;
    private Instrumentation mOrigInstrumentation = null;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init(){

        startTrack();
    }

    public boolean startTrack() {
        boolean result = true;
        try {
            mActivityThreadObj = ReflectHelper.invokeMethod(
                    "android.app.ActivityThread",
                    "currentActivityThread",
                    null,
                    (Class<?>[]) null,
                    (Object[]) null
            );

            if (mActivityThreadObj == null)
                throw new IllegalStateException("Failed to get CurrentActivityThread.");

            mOrigInstrumentation = (Instrumentation) ReflectHelper.getField(
                    mActivityThreadObj.getClass(),
                    "mInstrumentation",
                    mActivityThreadObj
            );

            if (mOrigInstrumentation == null)
                throw new IllegalStateException("Failed to get Instrumentation instance.");


            //表示已经被hack过了，不需要再搞一次
            if (mOrigInstrumentation.getClass().equals(MyInstrumentation.class)) {
                return true;
            }

            if (!(mOrigInstrumentation.getClass().equals(Instrumentation.class))) {
                throw new IllegalStateException("Not original Instrumentation instance, give up monitoring.");
            }

            ReflectHelper.setField(
                    mActivityThreadObj.getClass(),
                    "mInstrumentation",
                    new MyInstrumentation(),
                    mActivityThreadObj
            );

        } catch (Exception e) {
            result = false;
        }
        //mCanReflect = result;
        return result;

    }
}
