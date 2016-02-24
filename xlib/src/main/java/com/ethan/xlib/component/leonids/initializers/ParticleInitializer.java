package com.ethan.xlib.component.leonids.initializers;


import com.ethan.xlib.component.leonids.Particle;

import java.util.Random;

public interface ParticleInitializer {

	void initParticle(Particle p, Random r);

}
