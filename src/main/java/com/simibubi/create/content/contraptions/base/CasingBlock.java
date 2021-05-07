package com.simibubi.create.content.contraptions.base;

import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.lib.block.HarvestableBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class CasingBlock extends Block implements IWrenchable, HarvestableBlock {

	public CasingBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}

	@Override
	public ActionResultType onWrenched(BlockState state, ItemUseContext context) {
		return ActionResultType.FAIL;
	}

	@Override
	public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return player.getHeldItemMainhand().canHarvestBlock(state);
	}

	@Override
	public boolean isToolEffective(BlockState state, ToolItem tool) {
		return (tool instanceof PickaxeItem || tool instanceof AxeItem);
	}

}
