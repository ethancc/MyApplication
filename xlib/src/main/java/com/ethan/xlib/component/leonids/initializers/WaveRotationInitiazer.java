package com.ethan.xlib.component.leonids.initializers;


import com.ethan.xlib.component.leonids.Particle;

import java.util.Random;

/**
 * 支持摇摆的初始化器
 */
public class WaveRotationInitiazer implements ParticleInitializer {

	private int minWaveDuration;
	private int maxWaveDuration;

	private int minWaveAngle;
	private int maxWaveAngle;


	public WaveRotationInitiazer(int minWaveAngle, int maxWaveAngle, int minWaveDuration, int maxWaveDuration) {

		if (minWaveAngle < 0) {
			minWaveAngle = 0;
		}

		this.minWaveAngle = minWaveAngle;
		this.maxWaveAngle = maxWaveAngle;

		if (minWaveDuration < 0) {
			minWaveDuration = 0;
		}

		this.minWaveDuration = minWaveDuration;
		this.maxWaveDuration = maxWaveDuration;
	}

	@Override
	public void initParticle(Particle p, Random r) {
		int value = r.nextInt(maxWaveAngle - minWaveAngle) + minWaveAngle;
		p.mWaveFromDegree = -value / 2;
		p.mWaveToDegree = value / 2;

		int duration = r.nextInt(maxWaveDuration - minWaveDuration) + minWaveDuration;

		p.mWaveDuration = duration;
	}

}
