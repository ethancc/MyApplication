package com.ethan.xlib.component.leonids;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.Log;

import com.ethan.xlib.component.leonids.modifiers.ParticleModifier;

import java.util.List;


public class Particle {

	protected Bitmap mImage;
	
	public float mCurrentX;
	public float mCurrentY;
	
	public float mScale = 1f;
	public int mAlpha = 255;
	
	public float mInitialRotation = 0f;
	
	public float mRotationSpeed = 0f;
	
	public float mSpeedX = 0f;
	public float mSpeedY = 0f;

	public float mAccelerationX;
	public float mAccelerationY;

	private Matrix mMatrix;
	private Paint mPaint;

	private float mInitialX;
	private float mInitialY;

	public float mRotation;

	private long mTimeToLive;

	protected long mStartingMilisecond;

	private int mBitmapHalfWidth;
	private int mBitmapHalfHeight;

	private List<ParticleModifier> mModifiers;


	protected Particle() {		
		mMatrix = new Matrix();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
	}
	
	public Particle (Bitmap bitmap) {
		this();
		mImage = bitmap;
	}

	public void init() {
		mScale = 1;
		mAlpha = 255;	
	}
	
	public void configure(long timeToLive, float emiterX, float emiterY) {
		mBitmapHalfWidth = mImage.getWidth()/2;
		mBitmapHalfHeight = mImage.getHeight()/2;
		
		mInitialX = emiterX - mBitmapHalfWidth;
		mInitialY = emiterY - mBitmapHalfHeight;
		mCurrentX = mInitialX;
		mCurrentY = mInitialY;
		
		mTimeToLive = timeToLive;
	}

	public boolean update (long miliseconds) {

		long realMiliseconds = miliseconds - mStartingMilisecond;

		if (realMiliseconds > mTimeToLive) {
			return false;
		}

		mCurrentX = mInitialX + mSpeedX * realMiliseconds + mAccelerationX * realMiliseconds*realMiliseconds;
		mCurrentY = mInitialY + mSpeedY * realMiliseconds + mAccelerationY * realMiliseconds*realMiliseconds;

		mRotation = mInitialRotation + mRotationSpeed * realMiliseconds / 1000;

		// add by fortune
		m3DrotationY = m3DInitialRotationY + m3DRotationSpeedY * realMiliseconds / 1000;
		// add end

		// 应用所有的修改器
		for (int i=0; i<mModifiers.size(); i++) {
			mModifiers.get(i).apply(this, realMiliseconds);
		}

		return true;
	}
	
	public void draw (Canvas c) {
		DrawFilter orgDf = c.getDrawFilter();

		c.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG));

		mMatrix.reset();

		rotationY(mMatrix, m3DrotationY);

		mMatrix.postRotate(mRotation, mBitmapHalfWidth, mBitmapHalfHeight);
		mMatrix.postScale(mScale, mScale, mBitmapHalfWidth, mBitmapHalfHeight);

		mMatrix.postTranslate(mCurrentX, mCurrentY);

		mPaint.setAlpha(mAlpha);
		mPaint.setAntiAlias(true);

		c.drawBitmap(mImage, mMatrix, mPaint);

		c.setDrawFilter(orgDf);
	}

	public Particle activate(long startingMilisecond, List<ParticleModifier> modifiers) {
		mStartingMilisecond = startingMilisecond;
		// We do store a reference to the list, there is no need to copy, since the modifiers do not carte about states 
		mModifiers = modifiers;
		return this;
	}

	// add by fortune

	static final String TAG = Particle.class.getSimpleName();

	private Camera mCamera;

	private float mCenterX = 0;
	private float mCenterY = 0;

	// 3D旋转动画
	public float m3DInitialRotationY = 0f;
	public float m3DRotationSpeedY = 0f;
	public float m3DrotationY;

	// 摇摆时间
	public int mWaveDuration;
	public float mWaveFromDegree;
	public float mWaveToDegree;

	public float lastValue;
	public boolean reverse = false;

	private void rotationY(Matrix matrix, float rotation) {

		if (rotation == 0) {
			return;
		}

		if (mCenterX == 0 || mCenterY == 0) {
			mCenterX = mImage.getWidth() / 2;
			mCenterY = mImage.getHeight() / 2;
		}

		if (mCamera == null) {
			mCamera = new Camera();
		}

		mCamera.save();
		mCamera.rotateY(rotation);
		mCamera.getMatrix(matrix);
		mCamera.restore();

		matrix.preTranslate(-mCenterX, -mCenterY);
		matrix.postTranslate(mCenterX, mCenterY);

		Log.i(TAG, "rotationY:" + rotation + ", " + matrix);
	}
}
