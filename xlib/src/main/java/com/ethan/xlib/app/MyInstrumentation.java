package com.ethan.xlib.app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ethamhuang on 2016/1/27.
 */
public class MyInstrumentation extends Instrumentation {

    private final static String TAG = MyInstrumentation.class.getSimpleName();

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {

        /*if(!activity.getComponentName().equals("com.example.ethan.MapsActivity")){
            Intent intent = new Intent();
            //intent.setClass(activity,"com.example.ethan.MapsActivity");
            intent.setClassName(activity, "com.example.ethan.MapsActivity");
            activity.startActivity(intent);
            Log.d(TAG, "ethan >> MyInstrumentation activity MapsActivity");
        }else {

            super.callActivityOnCreate(activity, icicle);
        }*/

        super.callActivityOnCreate(activity, icicle);
        Log.d(TAG, "ethan >> MyInstrumentation.callActivityOnCreate");
    }

    public void execStartActivities(Context who, IBinder contextThread,
                                    IBinder token, Activity target, Intent[] intents, Bundle options){

        Log.d(TAG, "ethan >> MyInstrumentation.execStartActivities");
        /*super.execStartActivities(who, contextThread,
                token, target, intents, options);*/
    }
}
