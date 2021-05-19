package com.simibubi.create.lib.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface CanConnectRedstoneBlock {
	boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, Direction side);
}
