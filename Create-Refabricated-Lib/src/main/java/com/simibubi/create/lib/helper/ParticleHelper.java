package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.ParticleAccessor;

import net.minecraft.client.particle.Particle;

public final class ParticleHelper {
	public static boolean getField_21507(Particle particle) {
		return ((ParticleAccessor) particle).create$field_21507();
	}

	public static void setField_21507(Particle particle, boolean bool) {
		((ParticleAccessor) particle).create$field_21507(bool);
	}

	private ParticleHelper() {}
}
