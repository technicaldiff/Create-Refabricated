package com.simibubi.create.foundation.tileEntity.behaviour.linked;

import java.util.Arrays;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.RaycastHelper;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class LinkHandler {

	public static ActionResultType onBlockActivated(PlayerEntity player, World world, Hand hand, BlockRayTraceResult blockRayTraceResult) {
		if (player.isSneaking() || player.isSpectator())
			return ActionResultType.PASS;

		LinkBehaviour behaviour = TileEntityBehaviour.get(world, blockRayTraceResult.getPos(), LinkBehaviour.TYPE);
		if (behaviour == null)
			return ActionResultType.PASS;

		ItemStack heldItem = player.getHeldItem(hand);
		BlockRayTraceResult ray = RaycastHelper.rayTraceRange(world, player, 10);
		if (ray == null)
			return ActionResultType.PASS;
		if (AllItems.LINKED_CONTROLLER.isIn(heldItem))
			return ActionResultType.PASS;
		if (AllItems.WRENCH.isIn(heldItem))
			return ActionResultType.PASS;

		for (boolean first : Arrays.asList(false, true)) {
			if (behaviour.testHit(first, blockRayTraceResult.getHitVec())) {
				if (!world.isRemote)
					behaviour.setFrequency(first, player.getHeldItem(hand));
				world.playSound(null, blockRayTraceResult.getPos(), SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, .25f, .1f);
				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.PASS;
	}

}
