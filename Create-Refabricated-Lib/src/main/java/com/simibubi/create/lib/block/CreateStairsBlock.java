package com.simibubi.create.lib.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class CreateStairsBlock extends StairsBlock {

	/**
	 * {@link StairsBlock#StairsBlock(BlockState, Properties)} is protected so this allows us to use it.
	 */
	public CreateStairsBlock(BlockState blockState, Properties properties) {
		super(blockState, properties);
	}
}
