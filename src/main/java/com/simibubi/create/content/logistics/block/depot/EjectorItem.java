package com.simibubi.create.content.logistics.block.depot;

import com.tterrag.registrate.fabric.EnvExecutor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.api.EnvType;

public class EjectorItem extends BlockItem {

	public EjectorItem(Block p_i48527_1_, Properties p_i48527_2_) {
		super(p_i48527_1_, p_i48527_2_);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx) {
		PlayerEntity player = ctx.getPlayer();
		if (player != null && player.isSneaking())
			return ActionResultType.SUCCESS;
		return super.onItemUse(ctx);
	}

	@Override
	protected BlockState getStateForPlacement(BlockItemUseContext p_195945_1_) {
		BlockState stateForPlacement = super.getStateForPlacement(p_195945_1_);
		return stateForPlacement;
	}

	@Override
	protected boolean onBlockPlaced(BlockPos pos, World world, PlayerEntity p_195943_3_, ItemStack p_195943_4_,
		BlockState p_195943_5_) {
		if (world.isRemote)
			EnvExecutor.runWhenOn(EnvType.CLIENT, () -> () -> EjectorTargetHandler.flushSettings(pos));
		return super.onBlockPlaced(pos, world, p_195943_3_, p_195943_4_, p_195943_5_);
	}

	@Override
	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World world, BlockPos pos,
		PlayerEntity p_195938_4_) {
		return !p_195938_4_.isSneaking();
	}

}
