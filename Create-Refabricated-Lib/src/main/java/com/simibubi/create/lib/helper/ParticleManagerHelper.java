package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.ParticleManagerExtensions;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

/**
 * Removal of this class should be considered because Fabric API provides almost an exact replacement.
 * Use the Fabric API version instead of this class, unless issues arise.
 * @see net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
 */
@Deprecated
public final class ParticleManagerHelper {
	public static <T extends IParticleData> void registerFactory(ParticleManager $this, ParticleType<T> type, ParticleManager.IParticleMetaFactory<T> factory) {
		get($this).create$registerFactory0(type, factory);
	}

	public static <T extends IParticleData> void registerFactory(ParticleManager $this, ParticleType<T> type, IParticleFactory<T> factory) {
		get($this).create$registerFactory1(type, factory);
	}

	private static ParticleManagerExtensions get(ParticleManager manager) {
		return MixinHelper.cast(manager);
	}

	private ParticleManagerHelper() {}
}
