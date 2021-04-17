package com.simibubi.create.lib.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface HarvestableBlock {
	boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player);

	boolean isToolEffective(BlockState state, ToolItem tool);
}
