package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.ParticleAccessor;

import com.simibubi.create.lib.mixin.accessor.ServerPlayNetHandlerAccessor;
import com.simibubi.create.lib.mixin.accessor.Vector3fAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.client.particle.Particle;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.util.math.vector.Vector3f;

public class ParticleHelper {
	public static boolean getField_21507(Particle particle) {
		return ((ParticleAccessor) particle).create$field_21507();
	}

	public static void setField_21507(Particle particle, boolean bool) {
		((ParticleAccessor) particle).create$field_21507(bool);
	}
}
