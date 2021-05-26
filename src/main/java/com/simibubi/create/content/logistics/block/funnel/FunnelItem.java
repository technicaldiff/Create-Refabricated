package com.simibubi.create.content.logistics.block.funnel;

import com.simibubi.create.foundation.advancement.AllTriggers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class FunnelItem extends BlockItem {

	public FunnelItem(Block p_i48527_1_, Properties p_i48527_2_) {
		super(p_i48527_1_, p_i48527_2_);
	}

	public static ActionResultType funnelItemAlwaysPlacesWhenUsed(PlayerEntity player, World world, Hand hand, BlockRayTraceResult hitResult) {
		if (player.getHeldItem(hand)
			.getItem() instanceof FunnelItem)
			return ActionResultType.FAIL;
		return ActionResultType.PASS;
	}

	@Override
	protected BlockState getStateForPlacement(BlockItemUseContext ctx) {
		World world = ctx.getWorld();
		BlockPos pos = ctx.getPos();
		BlockState state = super.getStateForPlacement(ctx);
		if (state == null)
			return state;
		if (!(state.getBlock() instanceof FunnelBlock))
			return state;
		if (state.get(FunnelBlock.FACING)
			.getAxis()
			.isVertical())
			return state;

		Direction direction = state.get(FunnelBlock.FACING);
		FunnelBlock block = (FunnelBlock) getBlock();
		Block beltFunnelBlock = block.getEquivalentBeltFunnel(world, pos, state)
			.getBlock();
		BlockState equivalentBeltFunnel = beltFunnelBlock.getStateForPlacement(ctx)
			.with(BeltFunnelBlock.HORIZONTAL_FACING, direction);
		if (BeltFunnelBlock.isOnValidBelt(equivalentBeltFunnel, world, pos)) {
			AllTriggers.triggerFor(AllTriggers.BELT_FUNNEL, ctx.getPlayer());
			return equivalentBeltFunnel;
		}

		return state;
	}

}
