package com.simibubi.create.lib.extensions.helper;

import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

import com.simibubi.create.lib.extensions.ParticleManagerExtensions;
import com.simibubi.create.lib.utility.MixinHelper;

public final class ParticleManagerHelper {
	private ParticleManagerHelper() {}

	public static <T extends ParticleEffect> void registerFactory(ParticleManager $this, ParticleType<T> type, ParticleManager.SpriteAwareFactory<T> factory) {
		get($this).create$registerFactory0(type, factory);
	}

	public static <T extends ParticleEffect> void registerFactory(ParticleManager $this, ParticleType<T> type, ParticleFactory<T> factory) {
		get($this).create$registerFactory1(type, factory);
	}

	private static ParticleManagerExtensions get(ParticleManager manager) {
		return MixinHelper.cast(manager);
	}
}
