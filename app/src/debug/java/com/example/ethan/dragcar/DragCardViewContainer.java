package com.example.ethan.dragcar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.ethan.xlib.common.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by ethamhuang on 2016/2/22.
 */
public class DragCardViewContainer extends FrameLayout implements View.OnTouchListener{

    private  final  static int MAX_DISPLAY_NUM = 3; //最多每次显示3个
    private final  static  float BOUNCE = 1.5f;
    private final  static  long DURATION = 300;  //动画时长

    private ArrayList<DragCardView> dragCardViews = new ArrayList<>();
    private ArrayList<DragCardView.GameCard> gameCards;

    //游戏卡片的UI变化规律
    private final static UIProperty Card_UiProperty[] = {
            UIProperty.uiProperty(1f, 1f, 240),
            UIProperty.uiProperty(0.8f, 0.8f, 160),
            UIProperty.uiProperty(0.5f, 0.64f, 80),
    };

    public DragCardViewContainer(Context context) {
        this(context, null);
    }

    public DragCardViewContainer(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public DragCardViewContainer(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                lastRawY = event.getRawY();
                lastRawX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                updateCardsUI(v, event);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                resetUI();
                break;
        }

        return true;
    }


    public void addGameCardList(ArrayList<DragCardView.GameCard> list) {
        gameCards = list;
        if (gameCards == null || gameCards.isEmpty()) {
            removeAllViews();
            dragCardViews.clear();
            return;
        }

        updateDataSource();
    }


    float lastX, lastY;
    boolean isInResetUI; //正在重置UI
    boolean isViewNextAnimation; //正在显示下一个的过渡中
    float lastRawX, lastRawY;

    //更新卡片UI
    protected void updateCardsUI(View v, MotionEvent event) {
        LogUtil.d("ethan updateCardsUI v:" + v.toString());
        float dx = event.getX() - lastX;
        float dy = event.getY() - lastY;

        float rdx = event.getRawX() - lastRawX;
        float rdy = event.getRawY() - lastRawY;

        //阻力系数，值越小阻力越大
        float resistance_coefficient = 0.5f;
        v.setY(v.getTop() + rdy * resistance_coefficient);

        boolean anti_shake = Math.abs(dx) > 5;//防抖动
        if (anti_shake) {
            //移动
            v.setX(v.getX() + dx);
            float rotation = 20f;
            //旋转
            if (rdx < 0) {
                v.setRotation(Math.max(-rotation, rdx / rotation));
            } else {
                v.setRotation(Math.min(rotation, rdx / rotation));
            }
        }
        for (int i = 0; i < dragCardViews.size(); i++) {
            View child = dragCardViews.get(i);
            if (child == v) {
                continue;
            }
            if (child instanceof DragCardView) {
                if (anti_shake) {
                    child.setX(child.getX() + dx * 0.5f / i);
                    child.setRotation(v.getRotation() * 0.8f / i);
                }
                float change_rate = Math.abs(rdx) / v.getWidth() * 0.3f;
                float scale = Card_UiProperty[i].scale + Math.min(0.2f, change_rate);
                child.setScaleX(scale);
                child.setScaleY(scale);
//               child.setAlpha(CARD_ALPHA[i] + change_rate * 0.5f);
            }
        }
    }

    public void  resetUI() {
        isInResetUI = true;
        for (int i = 0; i < dragCardViews.size(); i++) {
            final View child = dragCardViews.get(i);
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator rotation = ObjectAnimator.ofFloat(child, "rotation", child.getRotation(),0.0f,0.0F);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(child, "scaleX", Card_UiProperty[i].scale);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(child, "scaleY",Card_UiProperty[i].scale);
            ObjectAnimator x = ObjectAnimator.ofFloat(child, "x", child.getLeft());
            ObjectAnimator y = ObjectAnimator.ofFloat(child, "y", child.getTop());

            set.setInterpolator(new OvershootInterpolator(BOUNCE /*弹回效果*/));
            set.playTogether(rotation, scaleX, scaleY, x, y);
            set.setDuration(DURATION);
            set.start();
            set.addListener(new EmptyAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isInResetUI = false;
                }
            });
        }
    }

    private void updateDataSource() {
        if (gameCards == null || gameCards.size() == 0 || listener == null) {
            return;
        }
        int total = Math.min(gameCards.size(), MAX_DISPLAY_NUM);
        removeAllViews();
        for (int i = 0; i < total; i++) {
            DragCardView.GameCard gameCard =  gameCards.get(i);
            DragCardView cardView;
            if (i <  dragCardViews.size()) {
                cardView = dragCardViews.get(i);
                listener.getDragCardView(cardView, gameCard, i);
            } else {
                cardView = listener.getDragCardView(null, gameCard, i);
                setGameCardUILayoutParams(cardView, i);
                dragCardViews.add(cardView);
            }

            addView(cardView, 0);
            cardView.setGameCard(gameCard);
        }

        if (dragCardViews.size() > 0) {
            dragCardViews.get(0).setOnTouchListener(this);
        }
    }

    private void  setGameCardUILayoutParams(View child,  int index) {
        //测试大小
        float density =  getResources().getDisplayMetrics().density ;
        int maxWidth = (int)(240 * density );
        int maxHeight =  (int)(300 * density);

        LayoutParams lp = new LayoutParams(maxWidth , maxHeight);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        UIProperty property =  Card_UiProperty[index];
        lp.topMargin = (int) (property.topMargin - maxHeight * (1-property.scale) / 2) ;
        child.setAlpha(property.alpha);
        child.setScaleX(property.scale);
        child.setScaleY(property.scale);
        child.setLayoutParams(lp);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class EmptyAnimatorListener implements   Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    public interface OnRefreshGameCardViewListener {
        DragCardView getDragCardView(DragCardView cardView, DragCardView.GameCard gameCard, int position);
    }

    private OnRefreshGameCardViewListener listener;
    public OnRefreshGameCardViewListener getListener() {
        return listener;
    }
    public void setListener(OnRefreshGameCardViewListener listener) {
        this.listener = listener;
        if (listener != null) {
            updateDataSource();
        }
    }

    public static class UIProperty {
        public float alpha  = 1;
        public float scale = 1;
        public int topMargin = 240;

        public  static UIProperty uiProperty(float alpha, float scale, int topMargin) {
            UIProperty uiProperty = new UIProperty();
            uiProperty.alpha = alpha;
            uiProperty.scale = scale;
            uiProperty.topMargin = topMargin;
            return uiProperty;
        }
    }
}
