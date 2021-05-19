package com.simibubi.create.lib.extensions;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public interface BlockExtensions {
	SoundType create$getSoundType(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity);

	int create$getLightValue(BlockState state, IBlockReader world, BlockPos pos);
}
