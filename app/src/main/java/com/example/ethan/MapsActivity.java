package com.example.ethan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ethan.xlib.component.UncaughtExceptionHandler;
import com.ethan.xlib.view.Chatplug_EditText;
import com.ethan.xlib.view.PDModeBitmapDrawable;
import com.example.ethan.myapplication.R;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.example.ethan.utils.ThreadLockTest;



public class MapsActivity extends FragmentActivity implements Handler.Callback{

    //private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    RefWatcher refWatcher = null;

    Cat cat = null;

    private Handler handler = null;

    private ImageView imageView;

    private Chatplug_EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        refWatcher = LeakCanary.install(getApplication());

        handler = new Handler(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        setImageView();

        input = findViewByIdEx(R.id.input);

        /*cat = new Cat();
        Box box = new Box();
        Doc doc = new Doc();

        box.cat = cat;
        doc.container = box;

        Intent intent = new Intent();
        intent.putExtra("aaa", "aaaa");
        Uri uri = intent.getData();
        Log.w("ethan", uri.toString());*/

        /*new Thread(){

            @Override
            public void run() {
                ThreadLockTest.getInstance();
            }
        }.start();*/

        UncaughtExceptionHandler.getInstance().register();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //refWatcher.watch(this);
    }


//    public List<>

    public void exit(View v){
        Intent activity = new Intent(this, SettingsActivity.class);
        //Intent activity = new Intent(this, MapsActivity.class);
        startActivity(activity);
        finish();

        /*//refWatcher.watch(cat);
        /*File f = getDatabasePath("a").getParentFile();
        if(f != null){

            Toast.makeText(this, f.getAbsolutePath() + " ### " + f.isFile() + " ### " + f.exists(), Toast.LENGTH_SHORT).show();
        }

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image*//*");
        startActivityForResult(Intent.createChooser(i, "ѡ���ļ�"), 10);*/

        //ThreadLockTest.getInstance();
        //handler.sendEmptyMessageDelayed(1, 1000L);

        /*new Thread(){
            @Override
            public void run() {
                ThreadLockTest.getInstance();
            }
        }.start();*/

        //input.insetText(0, 40);

    }

    protected <T extends View> T findViewByIdEx(int id){
        return (T)findViewById(id);
    }

    private void setImageView(){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        PDModeBitmapDrawable drawable = new PDModeBitmapDrawable(getResources(), bm);
        imageView.setImageDrawable(drawable);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.i("ethan", "onUserLeaveHint >> 1");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("ethan", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ethan", "onStop");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("ethan", "onBackPressed");
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case 1:
                ThreadLockTest.getInstance();
                break;
            default:
                break;
        }


        return true;
    }

    class Cat{

    }

    class Box{
        Cat cat;
    }

    static class Doc{
        public static Box container;
    }
}
