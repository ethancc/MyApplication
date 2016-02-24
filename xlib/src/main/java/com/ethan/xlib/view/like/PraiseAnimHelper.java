package com.ethan.xlib.view.like;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ethan.xlib.R;
import com.ethan.xlib.component.leonids.ParticleSystem;
import com.ethan.xlib.component.leonids.modifiers.AlphaModifier;
import com.ethan.xlib.component.leonids.modifiers.ScaleModifier;

/**
 * 点赞动画的辅助类
 */
public class PraiseAnimHelper {

    private static final int[] resIds = new int[]{
            R.drawable.ic_liked2_02,
            R.drawable.ic_liked2_03,
            R.drawable.ic_liked2_04,
            R.drawable.ic_liked2_05,
            R.drawable.ic_liked2_06,
            R.drawable.ic_ele2_03,
            R.drawable.ic_liked2_07,
            R.drawable.ic_liked2_08,
            R.drawable.ic_liked2_09,
            R.drawable.ic_liked2_10,
            R.drawable.ic_liked2_11,
            R.drawable.ic_ele2_08
    };

    private static final int[] heartExplodeCold = new int[]{
            R.drawable.ic_liked2_01,
            R.drawable.ic_liked2_05,
            R.drawable.ic_liked2_07,
            R.drawable.ic_liked2_08,
    };

    private static final int[] heartExplodeWarm = new int[]{
            R.drawable.ic_liked2_09,
            R.drawable.ic_liked2_10,
            R.drawable.ic_liked2_06,
            R.drawable.ic_liked2_02,
    };

    private static final int[] heartExplodeMixture = new int[]{
            R.drawable.ic_liked2_09,
            R.drawable.ic_liked2_07,
            R.drawable.ic_liked2_11,
            R.drawable.ic_liked2_06,
            R.drawable.ic_liked2_02,
            R.drawable.ic_liked2_08,
    };

    private int currentFlingImageIndex = 0;
    private int popCount = 0;

    private int currentExplodeIndex = 0;

    // 心跳动画
    private Animation popAnim;

    private Handler handler = new Handler(Looper.getMainLooper());

    private Runnable clearPopCountTask = new Runnable() {
        @Override
        public void run() {
            popCount = 0;
            currentFlingImageIndex = 0;
        }
    };

    private View likeVew;

    public PraiseAnimHelper(View likeVew) {
        this.likeVew = likeVew;
        initAnim();
    }

    private void initAnim() {
        popAnim = AnimationUtils.loadAnimation(likeVew.getContext(), R.anim.anim_heart_pop);
    }

    /**
     * 播放点赞动画
     * 播放逻辑为，一次播放一个心，5个心之后播放一个手游宝图标，10次之后播放一次爆炸效果
     * @param isAnimPop 播放动画的时候心是否跳动
     */
    public void playLikeAnim(boolean isAnimPop) {
        if (isAnimPop) {
            likeVew.startAnimation(popAnim);
        }

        // 3秒之内没做连击，则10次爆炸效果从头开始计数
        handler.removeCallbacks(clearPopCountTask);
        handler.postDelayed(clearPopCountTask, 3000);

        if (popCount >= 19) {
            // 20次播放一次爆炸效果
            popCount = 0;
            nextHeartExplodeAnim(likeVew);
        } else {
            // 播放心形飘动效果
            nextHeartFlingAnim(likeVew);
            popCount++;
        }
    }

    private void nextHeartFlingAnim(View v) {
        if (currentFlingImageIndex >= resIds.length) {
            currentFlingImageIndex = 0;
        }
        playHeartFlingAnim(v, resIds[currentFlingImageIndex]);
        currentFlingImageIndex ++;
    }

    private void nextHeartExplodeAnim(View v) {
        if (currentExplodeIndex > 2) {
            currentExplodeIndex = 0;
        }

        // 爆炸效果的规律为：暖色、冷色、混色
        switch (currentExplodeIndex) {
            case 0:
                playHeartExplodeAnim(v, heartExplodeWarm);
                break;
            case 1:
                playHeartExplodeAnim(v, heartExplodeCold);
                break;
            case 2:
                playHeartExplodeAnim(v, heartExplodeMixture);
                break;
        }
        currentExplodeIndex ++;
    }

    // 心型飘动动画
    private static void playHeartFlingAnim(View v, int drawableId) {
        // check context
        Context context = v.getContext();
        if (!(context instanceof Activity)) {
            return;
        }
        Activity activity = (Activity) context;
        ParticleSystem ps = new ParticleSystem(activity, 100, drawableId, 2500);

        // 运行速度范围
        ps.setScaleRange(0.5f, 1f);

        // 飘动的范围角度
        ps.setSpeedModuleAndAngleRange(0.07f, 0.12f, 260, 280);

        // 摇摆速度和范围
        ps.setWaveRotationSpeedRange(0, 90, 500, 2000);

        // 动画效果为：从最小弹出，然后放大，最后渐变消失
        ps.addModifier(new ScaleModifier(0.0f, 0.6f, 0, 200));
        ps.addModifier(new ScaleModifier(0.6f, 1f, 200, 2000));
        ps.addModifier(new AlphaModifier(200, 0, 1500, 2500));

        // 一次弹出一颗动画，从View的顶部弹出
        ps.oneShot(v, 1, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    }

    // 爆炸动画
    private static void playHeartExplodeAnim(View v, int[] resIds) {
        Context context = v.getContext();
        if (!(context instanceof Activity)) {
            return;
        }

        Activity activity = (Activity) context;
        ParticleSystem ps = new ParticleSystem(activity, 200, resIds, 800);

        ps.setScaleRange(0.35f, 0.35f);
        ps.setSpeedRange(0.3f, 0.35f);
        // 去掉3D旋转
        //  ps.set3DRotationSpeedRange(360, 1080);

        ps.addModifier(new AlphaModifier(0, 0, 0, 50));
        ps.addModifier(new AlphaModifier(0, 255, 50, 100));
        ps.addModifier(new AlphaModifier(255, 0, 700, 800));
        ps.emit(v, 50, 800);
    }
}
