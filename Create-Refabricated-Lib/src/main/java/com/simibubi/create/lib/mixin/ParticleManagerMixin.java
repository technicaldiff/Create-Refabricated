package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.simibubi.create.lib.extensions.ParticleManagerExtensions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

@Deprecated
@Environment(EnvType.CLIENT)
@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin implements ParticleManagerExtensions {
	@Shadow
	protected abstract <T extends IParticleData> void registerFactory(ParticleType<T> particleType, ParticleManager.IParticleMetaFactory<T> spriteAwareFactory);

	@Shadow
	protected abstract <T extends IParticleData> void registerFactory(ParticleType<T> type, IParticleFactory<T> factory);

	@Override
	public <T extends IParticleData> void create$registerFactory0(ParticleType<T> particleType, ParticleManager.IParticleMetaFactory<T> spriteAwareFactory) {
		registerFactory(particleType, spriteAwareFactory);
	}

	@Override
	public <T extends IParticleData> void create$registerFactory1(ParticleType<T> type, IParticleFactory<T> factory) {
		registerFactory(type, factory);
	}
}
