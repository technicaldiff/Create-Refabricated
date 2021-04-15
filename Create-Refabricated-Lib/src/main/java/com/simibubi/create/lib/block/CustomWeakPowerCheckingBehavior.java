package com.simibubi.create.lib.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public interface CustomWeakPowerCheckingBehavior {
	boolean shouldCheckWeakPower(BlockState state, IWorldReader world, BlockPos pos, Direction side);
}
