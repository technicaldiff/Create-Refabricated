package com.simibubi.create.lib.block;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface SlopeCreationCheckingRail {
	public boolean canMakeSlopes(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos);
}
