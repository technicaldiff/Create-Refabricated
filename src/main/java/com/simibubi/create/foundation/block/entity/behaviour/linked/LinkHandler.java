package com.simibubi.create.foundation.block.entity.behaviour.linked;

import com.simibubi.create.foundation.block.entity.BlockEntityBehaviour;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import java.util.Arrays;

public class LinkHandler {

	public static ActionResult onBlockActivated(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult) {
		if (player.isSneaking() || player.isSpectator())
			return ActionResult.PASS;

		LinkBehaviour behaviour = BlockEntityBehaviour.get(world, blockHitResult.getBlockPos(), LinkBehaviour.TYPE);
		if (behaviour == null)
			return ActionResult.PASS;

		for (boolean first : Arrays.asList(false, true)) {
			if (behaviour.testHit(first, blockHitResult.getPos())) {
				if (!world.isClient) behaviour.setFrequency(first, player.getStackInHand(hand));
				world.playSound(null, blockHitResult.getBlockPos(), SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, .25f, .1f);
				return ActionResult.SUCCESS;
			}
		}

		return ActionResult.PASS;
	}
}
