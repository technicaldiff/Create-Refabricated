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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ItemUseOverrides {

	private static final Set<ResourceLocation> OVERRIDES = new HashSet<>();

	public static void addBlock(Block block) {
		OVERRIDES.add(Registry.BLOCK.getKey(block));
	}

	public static ActionResultType onBlockActivated(PlayerEntity player, World world, Hand hand, BlockRayTraceResult traceResult) {
		if (AllItems.WRENCH.isIn(player.getHeldItem(hand)))
			return ActionResultType.PASS;

		BlockState state = world
				.getBlockState(traceResult.getPos());
		ResourceLocation id = Registry.BLOCK.getKey(state.getBlock());

		if (!OVERRIDES.contains(id))
			return ActionResultType.PASS;

		BlockRayTraceResult blockTrace =
				new BlockRayTraceResult(VecHelper.getCenterOf(traceResult.getPos()), traceResult.getFace(), traceResult.getPos(), true);
		ActionResultType result = state.onUse(world, player, hand, blockTrace);

		if (!result.isAccepted())
			return ActionResultType.PASS;
		else
			return result;

	}

}
