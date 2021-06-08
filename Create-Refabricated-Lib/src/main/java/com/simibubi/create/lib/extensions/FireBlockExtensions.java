package com.simibubi.create.lib.extensions;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface FireBlockExtensions {
	default boolean canCatchFire(IBlockReader world, BlockPos pos, Direction face) {
		return ((BlockStateExtensions) world.getBlockState(pos)).create$isFlammable(world, pos, face);
	}

	int doFunc_220274_q(BlockState state);
}
