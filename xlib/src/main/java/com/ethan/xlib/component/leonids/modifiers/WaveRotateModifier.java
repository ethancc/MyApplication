package com.ethan.xlib.component.leonids.modifiers;


import com.ethan.xlib.component.leonids.Particle;

/**
 * 支持摇摆的修改器
 */
public class WaveRotateModifier implements ParticleModifier {

	@Override
	public void apply(Particle particle, long miliseconds) {
		if (particle.mWaveDuration == 0) {
			return;
		}

		// 首先将时间分片，然后求出分片后的事件因子
		long time = miliseconds % particle.mWaveDuration;
		float t = (float) time / (float) (particle.mWaveDuration);

//		Log.d("WaveRotateModifier", "t:" + t);

		// 如果当前值比上一次的值小，表示一次循环
		if (t < particle.lastValue) {
			particle.reverse = !particle.reverse;
		}

		// 记录当前值为上一次的值
		particle.lastValue = t;

		// 处理反向
		float interpolatedTime = particle.reverse ? 1.0f - t : t;

		// 计算摇摆的角度
		float degrees = particle.mWaveFromDegree + ((particle.mWaveToDegree - particle.mWaveFromDegree) * interpolatedTime);

		// 设置角度
		particle.mRotation = degrees;
	}
}
