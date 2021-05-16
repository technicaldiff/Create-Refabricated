package com.simibubi.create.lib.extensions;

import net.minecraft.particles.BlockParticleData;
import net.minecraft.util.math.BlockPos;

public interface BlockParticleDataExtensions {
	public BlockParticleData setPos(BlockPos pos);

	public BlockPos getPos();
}
