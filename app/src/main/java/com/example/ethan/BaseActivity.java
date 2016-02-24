package com.example.ethan;

import android.app.Activity;
import android.view.View;

/**
 * Created by ethamhuang on 2016/2/23.
 */
public class BaseActivity extends Activity {

    protected <T extends View> T findViewByIdEx(int id){
        return (T) findViewById(id);
    }
}
