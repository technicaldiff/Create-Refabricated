package com.simibubi.create.content.contraptions.particle;

import com.mojang.serialization.Codec;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleManager.IParticleMetaFactory;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.IParticleData.IDeserializer;
import net.minecraft.particles.ParticleType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface ICustomParticleDataWithSprite<T extends IParticleData> extends ICustomParticleData<T> {

	IDeserializer<T> getDeserializer();
	
	public default ParticleType<T> createType() {
		return new ParticleType<T>(false, getDeserializer()) {

			@Override
			public Codec<T> getCodec() {
				return ICustomParticleDataWithSprite.this.getCodec(this);
			}
		};
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	default IParticleFactory<T> getFactory() {
		throw new IllegalAccessError("This particle type uses a metaFactory!");
	}
	
	@Environment(EnvType.CLIENT)
	public IParticleMetaFactory<T> getMetaFactory();
	
	@Override
	@Environment(EnvType.CLIENT)
	public default void register(ParticleType<T> type, ParticleManager particles) {
		particles.registerFactory(type, getMetaFactory());
	}
	
}
