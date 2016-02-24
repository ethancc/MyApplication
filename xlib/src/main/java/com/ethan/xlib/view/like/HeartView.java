package com.ethan.xlib.view.like;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ethan.xlib.R;

/**
 * 点赞爱心View
 */
public class HeartView extends FrameLayout {

    public HeartView(Context context) {
        super(context);
        init();
    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private ImageView mHeartGray;
    private ImageView mHeartRed;

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.heart_support_view, null);
        mHeartGray = (ImageView) root.findViewById(R.id.heart_gray);
        mHeartRed = (ImageView) root.findViewById(R.id.heart_red);

        addView(root);
    }

    // 显示红心
    public void showGrayHeart(boolean anim) {
        //红心out 大小1 --> 0   颜色透明 1 --> 0
        //灰心in 大小2 --> 1   颜色透明 0 --> 0

        if (anim) {
            Animation animRedOut = AnimationUtils.loadAnimation(getContext(), R.anim.anim_heart_red_out);
            Animation animGrayIn = AnimationUtils.loadAnimation(getContext(), R.anim.anim_heart_gray_in);


            mHeartRed.startAnimation(animRedOut);
            mHeartGray.startAnimation(animGrayIn);
        }

        mHeartGray.setVisibility(View.VISIBLE);
        mHeartRed.setVisibility(View.INVISIBLE);
    }

    // 显示灰心
    public void showRedHeart(boolean anim) {
        //红心in 大小 0-->1   颜色透明 0-->1
        //灰心out 大小 1-->2   颜色透明 1-->0
        if (anim) {
            Animation animRedIn = AnimationUtils.loadAnimation(getContext(), R.anim.anim_heart_red_in);
            Animation animGrayOut = AnimationUtils.loadAnimation(getContext(), R.anim.anim_heart_gray_out);

            mHeartRed.startAnimation(animRedIn);
            mHeartGray.startAnimation(animGrayOut);
        }
        mHeartGray.setVisibility(View.INVISIBLE);
        mHeartRed.setVisibility(View.VISIBLE);

    }

    //=======================================
    // add by fortune
    //=======================================

    private PraiseAnimHelper likeAnimHelper;

    /**
     * 播放点赞动画
     * @param isAnimPop 播放动画的时候，是否开启心跳效果
     */
    public void playLikeAnim(boolean isAnimPop) {
        if (likeAnimHelper == null) {
            likeAnimHelper = new PraiseAnimHelper(this);
        }
        likeAnimHelper.playLikeAnim(isAnimPop);
    }
    // add end
}
