package com.ethan.xlib.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;

import com.ethan.xlib.R;

/**
 * Created by ethamhuang on 2015/11/4.
 */
public class PDModeBitmapDrawable extends BitmapDrawable {

    private PorterDuffXfermode mXfermode;
    private Bitmap mBorder;
    private Bitmap mMask;

    public PDModeBitmapDrawable(Resources res, Bitmap bitmap){
        super(res, bitmap);

        //mMask = bitmap;
        init(res);
    }

    private void init(Resources res){

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mBorder = BitmapFactory.decodeResource(res, R.drawable.channel_icon_stroke);
        mMask = BitmapFactory.decodeResource(res, R.drawable.channel_icon_mask);
    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Paint paint = this.getPaint();
        int sc = canvas.saveLayer(0, 0, getBounds().right - getBounds().left, getBounds().bottom - getBounds().top, null, Canvas.MATRIX_SAVE_FLAG);
        canvas.drawBitmap(mMask, null, getBounds(), paint);
        Xfermode oldXfermode =  paint.getXfermode();
        paint.setXfermode(mXfermode);
        super.draw(canvas);

        paint.setXfermode(oldXfermode);
        canvas.restoreToCount(sc);
        canvas.drawBitmap(mBorder, null, getBounds(), paint);
    }

    /**
     *  Paint paint = obtainPaint(mState.mDrawable);

     int saveFlags =
     Canvas.MATRIX_SAVE_FLAG
     //                        |
     //                Canvas.CLIP_SAVE_FLAG |
     //                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
     //                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
     //                Canvas.CLIP_TO_LAYER_SAVE_FLAG
     ;
     int sc = canvas.saveLayer(0, 0, getBounds().right - getBounds().left, getBounds().bottom - getBounds().top, null, saveFlags);

     canvas.drawBitmap(mMask, null, getBounds(), paint);
     Xfermode oldXfermode =  paint.getXfermode();
     paint.setXfermode(mXfermode);
     super.draw(canvas);
     paint.setXfermode(oldXfermode);
     canvas.restoreToCount(sc);
     canvas.drawBitmap(mBorder, null, getBounds(), paint);
     *
     * */
}
