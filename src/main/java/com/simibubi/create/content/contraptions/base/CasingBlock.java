package com.simibubi.create.content.contraptions.base;

import com.simibubi.create.content.contraptions.wrench.Wrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class CasingBlock extends Block implements Wrenchable {

	public CasingBlock(Settings p_i48440_1_) {
		super(p_i48440_1_);
	}

	@Override
	public ActionResult onWrenched(BlockState state, ItemUsageContext context) {
		return ActionResult.FAIL;
	}

	/*@Override
	public ToolType getHarvestTool(BlockState state) {
		return null;
	}

	@Override
	public boolean canHarvestBlock(BlockState state, BlockView world, BlockPos pos, PlayerEntity player) {
		for (ToolType toolType : player.getMainHandStack().getToolTypes()) {
			if (isToolEffective(state, toolType))
				return true;
		}		
		return super.canHarvestBlock(state, world, pos, player);
	}
	
	@Override
	public boolean isToolEffective(BlockState state, ToolType tool) {
		return tool == ToolType.AXE || tool == ToolType.PICKAXE;
	}*/

}
