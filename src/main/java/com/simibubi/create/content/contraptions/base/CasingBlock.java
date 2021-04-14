package com.simibubi.create.content.contraptions.base;

import com.simibubi.create.content.contraptions.wrench.IWrenchable;

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

public class CasingBlock extends Block implements IWrenchable {

	public CasingBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}

	@Override
	public ActionResultType onWrenched(BlockState state, ItemUseContext context) {
		return ActionResultType.FAIL;
	}

//	@Override
//	public ToolType getHarvestTool(BlockState state) { // todo
//		return null;
//	}

//	@Override
	public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
//		for (ToolType toolType : player.getHeldItemMainhand().getToolTypes()) {
//			if (isToolEffective(state, toolType))
//				return true;
//		}
//		return super.canHarvestBlock(state, world, pos, player); todo: see if this actually works
		return player.getHeldItemMainhand().canHarvestBlock(state);
	}

//	@Override
	public boolean isToolEffective(BlockState state, ToolItem tool) {
//		return tool == ToolType.AXE || tool == ToolType.PICKAXE; todo: see if this actually works
		return (tool instanceof PickaxeItem || tool instanceof AxeItem);
	}

}
