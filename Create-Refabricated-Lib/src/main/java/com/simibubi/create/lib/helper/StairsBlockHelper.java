package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.StairsBlockAccessor;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class StairsBlockHelper {
	public static StairsBlock init(BlockState blockState, Properties properties) {
		return StairsBlockAccessor.create$init(blockState, properties);
	}
}
