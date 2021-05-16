package com.simibubi.create.lib.extensions;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.util.math.BlockPos;

public interface DiggingParticle$FactoryExtensions {
	Particle makeParticleAtPos(BlockParticleData blockParticleData, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i);
	Particle updateSprite(BlockPos pos);
}
