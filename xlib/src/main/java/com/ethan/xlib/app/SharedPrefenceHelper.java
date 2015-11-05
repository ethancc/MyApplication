package com.ethan.xlib.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ethamhuang on 2015/10/30.
 */
public class SharedPrefenceHelper {

    private static final String SHARED_PREF_NAME = "__global_default_shared_prefence_";


    public static SharedPreferences getDefaultSharedPref(Context context){

        return context.getSharedPreferences(getDefaultFileName(), Context.MODE_PRIVATE);
    }



    private static String getDefaultFileName(){
        try {
            return MessageDigest.getInstance("MD5").digest(SHARED_PREF_NAME.getBytes("UTF-8")).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return SHARED_PREF_NAME;
    }
}
