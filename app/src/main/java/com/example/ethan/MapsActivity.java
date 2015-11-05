package com.example.ethan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ethan.xlib.component.UncaughtExceptionHandler;
import com.ethan.xlib.view.PDModeBitmapDrawable;
import com.example.ethan.myapplication.R;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.example.ethan.utils.ThreadLockTest;



public class MapsActivity extends FragmentActivity implements Handler.Callback{

    //private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private static String mStr = "aaa";

    RefWatcher refWatcher = null;

    Cat cat = null;

    private Handler handler = null;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        refWatcher = LeakCanary.install(getApplication());
        setStr();

        handler = new Handler(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        setImageView();

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

    private static void setStr(){
        mStr = "bbb";
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //refWatcher.watch(this);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        /*if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }*/
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

//    public List<>

    public void exit(View v){
        /*Intent activity = new Intent(this, SettingsActivity.class);
        Intent activity = new Intent(this, MapsActivity.class);
        startActivity(activity);
        finish();*/

        //refWatcher.watch(cat);
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

        String aa = null;
        aa.toString();

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
