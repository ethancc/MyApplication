package com.example.ethan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ethan.xlib.view.like.HeartView;
import com.example.ethan.myapplication.R;

/**
 * Created by ethamhuang on 2016/2/23.
 */
public class LikeClickActivity extends BaseActivity {

    private ListView list;
    private LikeListAdapter adater;
    private int[] datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_click);

        initDatas();

        list = findViewByIdEx(R.id.list);
        adater = new LikeListAdapter();
        list.setAdapter(adater);
    }

    private void initDatas(){
        datas = new int[10];
    }

    public static void show(Context context){

        if(context == null){
            return;
        }

        Intent activity = new Intent(context, LikeClickActivity.class);
        context.startActivity(activity);
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private class LikeListAdapter extends BaseAdapter implements View.OnClickListener{

        @Override
        public int getCount() {
            return datas.length;
        }

        @Override
        public Object getItem(int position) {

            if(position >= 0 && position < datas.length){
                return datas[position];
            }

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder h;
            if(convertView != null){
                h = (ViewHolder) convertView.getTag();
            }else{
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_like_item, null);

                h = new ViewHolder();
                convertView.setTag(h);
            }

            h.view = (HeartView) convertView.findViewById(R.id.heart_view);
            convertView.setOnClickListener(this);

            return convertView;
        }

        @Override
        public void onClick(View v) {
            ViewHolder h = (ViewHolder) v.getTag();
            h.view.playLikeAnim(true);
        }

        private class ViewHolder{
            public HeartView view;
        }
    }

}
