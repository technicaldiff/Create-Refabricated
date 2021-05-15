package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.BlockAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class BlockHelper {
	public static void dropXpOnBlockBreak(Block block, ServerWorld serverWorld, BlockPos blockPos, int i) {
		 get(block).create$dropXpOnBlockBreak(serverWorld, blockPos, i);
	}

	private static BlockAccessor get(Block block) {
		return MixinHelper.cast(block);
	}

	private BlockHelper() {}
}
