package com.ethan.xlib.component.leonids.initializers;


import com.ethan.xlib.component.leonids.Particle;

import java.util.Random;

/**
 * 3D旋转初始化
 */
public class ThreeDimenRotationYSpeedInitializer implements ParticleInitializer {

	private float mMinRotationSpeed;
	private float mMaxRotationSpeed;

	public ThreeDimenRotationYSpeedInitializer(float minRotationSpeed, float maxRotationSpeed) {
		mMinRotationSpeed = minRotationSpeed;
		mMaxRotationSpeed = maxRotationSpeed;
	}

	@Override
	public void initParticle(Particle p, Random r) {
		float rotationSpeed = r.nextFloat() * (mMaxRotationSpeed - mMinRotationSpeed) + mMinRotationSpeed;
		p.m3DRotationSpeedY = rotationSpeed;
	}

}
