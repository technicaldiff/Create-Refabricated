package com.simibubi.create;

import java.util.function.Supplier;

import com.simibubi.create.content.contraptions.fluids.particle.FluidParticleData;
import com.simibubi.create.content.contraptions.particle.AirFlowParticleData;
import com.simibubi.create.content.contraptions.particle.AirParticleData;
import com.simibubi.create.content.contraptions.particle.CubeParticleData;
import com.simibubi.create.content.contraptions.particle.HeaterParticleData;
import com.simibubi.create.content.contraptions.particle.ICustomParticleData;
import com.simibubi.create.content.contraptions.particle.RotationIndicatorParticleData;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.registry.Registry;

public enum AllParticleTypes {

	ROTATION_INDICATOR(RotationIndicatorParticleData::new),
	AIR_FLOW(AirFlowParticleData::new),
	AIR(AirParticleData::new),
	HEATER_PARTICLE(HeaterParticleData::new),
	CUBE(CubeParticleData::new),
	FLUID_PARTICLE(FluidParticleData::new),
	BASIN_FLUID(FluidParticleData::new),
	FLUID_DRIP(FluidParticleData::new)

	;

	private ParticleEntry<?> entry;

	<D extends IParticleData> AllParticleTypes(Supplier<? extends ICustomParticleData<D>> typeFactory) {
		String asId = Lang.asId(this.name());
		entry = new ParticleEntry<>(new ResourceLocation(Create.ID, asId), typeFactory);
	}

	public static void register() {
		for (AllParticleTypes particle : values())
			Registry.register(Registry.PARTICLE_TYPE, particle.entry.id, particle.get());
//			particle.entry.register(Registry.PARTICLE_TYPE);

	}

	@Environment(EnvType.CLIENT)
	public static void registerFactories() {
		ParticleManager particles = Minecraft.getInstance().particles;
		for (AllParticleTypes particle : values())
			particle.entry.registerFactory(particles);
	}

	public ParticleType<?> get() {
		return entry.getOrCreateType();
	}

	public String parameter() {
		return Lang.asId(name());
	}

	private class ParticleEntry<D extends IParticleData> {
		Supplier<? extends ICustomParticleData<D>> typeFactory;
		ParticleType<D> type;
		ResourceLocation id;

		public ParticleEntry(ResourceLocation id, Supplier<? extends ICustomParticleData<D>> typeFactory) {
			this.id = id;
			this.typeFactory = typeFactory;
		}

		void register(Registry<ParticleType<?>> registry) {
//			registry.register(getOrCreateType());
		}

		ParticleType<D> getOrCreateType() {
			if (type != null)
				return type;
			type = typeFactory.get()
				.createType();
//			type.setRegistryName(id);
			return type;
		}

		@Environment(EnvType.CLIENT)
		void registerFactory(ParticleManager particles) {
			typeFactory.get()
				.register(getOrCreateType(), particles);
		}

	}

}
