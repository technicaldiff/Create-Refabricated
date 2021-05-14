package com.simibubi.create.foundation.block;

import java.util.HashSet;
import java.util.Set;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.VecHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ItemUseOverrides {

	private static final Set<ResourceLocation> overrides = new HashSet<>();

	public static void addBlock(Block block) {
		overrides.add(block.getRegistryName());
	}

	public static ActionResultType onBlockActivated(PlayerEntity player, World world, Hand hand, BlockRayTraceResult traceResult) {
		if (AllItems.WRENCH.isIn(player.getHeldItem(hand)))
			return;

		BlockState state = world
			.getBlockState(traceResult.getPos());
		ResourceLocation id = state.getBlock()
			.getRegistryName();

		if (!overrides.contains(id))
			return;

		BlockRayTraceResult blockTrace =
			new BlockRayTraceResult(VecHelper.getCenterOf(traceResult.getPos()), traceResult.getFace(), traceResult.getPos(), true);
		ActionResultType result = state.onUse(world, player, hand, blockTrace);

		if (!result.isAccepted())
			return;

		event.setCanceled(true);
		event.setCancellationResult(result);

	}
}
