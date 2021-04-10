package com.simibubi.create.lib.extensions;

import org.jetbrains.annotations.ApiStatus;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

@Deprecated
public interface ParticleManagerExtensions {
	@ApiStatus.Internal
	<T extends IParticleData> void create$registerFactory0(ParticleType<T> particleType, ParticleManager.IParticleMetaFactory<T> spriteAwareFactory);

	@ApiStatus.Internal
	<T extends IParticleData> void create$registerFactory1(ParticleType<T> type, IParticleFactory<T> factory);
}
