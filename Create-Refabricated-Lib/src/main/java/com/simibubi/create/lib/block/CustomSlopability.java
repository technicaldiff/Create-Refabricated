package com.simibubi.create.lib.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public interface CustomSlopability {
	public boolean canMakeSlopes(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos);
}
