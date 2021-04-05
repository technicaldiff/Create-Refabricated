package com.simibubi.create.content.contraptions.particle;

import com.mojang.serialization.Codec;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.IParticleData.IDeserializer;
import net.minecraft.particles.ParticleType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface ICustomParticleData<T extends IParticleData> {

	IDeserializer<T> getDeserializer();

	Codec<T> getCodec(ParticleType<T> type); 
	
	public default ParticleType<T> createType() {
		return new ParticleType<T>(false, getDeserializer()) {

			@Override
			public Codec<T> getCodec() {
				return ICustomParticleData.this.getCodec(this);
			}
		};
	}
	
	@Environment(EnvType.CLIENT)
	public IParticleFactory<T> getFactory();
	
	@Environment(EnvType.CLIENT)
	public default void register(ParticleType<T> type, ParticleManager particles) {
		particles.registerFactory(type, getFactory());
	}
	
}
