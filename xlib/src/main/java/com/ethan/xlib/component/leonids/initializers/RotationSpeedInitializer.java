package com.ethan.xlib.component.leonids.initializers;


import com.ethan.xlib.component.leonids.Particle;

import java.util.Random;

public class RotationSpeedInitializer implements ParticleInitializer {

	private float mMinRotationSpeed;
	private float mMaxRotationSpeed;

	public RotationSpeedInitializer(float minRotationSpeed,	float maxRotationSpeed) {
		mMinRotationSpeed = minRotationSpeed;
		mMaxRotationSpeed = maxRotationSpeed;
	}

	@Override
	public void initParticle(Particle p, Random r) {
		float rotationSpeed = r.nextFloat() * (mMaxRotationSpeed-mMinRotationSpeed) + mMinRotationSpeed;
		p.mRotationSpeed = rotationSpeed;
	}

}
