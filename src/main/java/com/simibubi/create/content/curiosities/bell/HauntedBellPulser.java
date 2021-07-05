package com.simibubi.create.content.curiosities.bell;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.networking.AllPackets;

import com.simibubi.create.lib.event.PlayerTickEndCallback;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;

public class HauntedBellPulser {

	public static final int DISTANCE = 3;
	public static final int RECHARGE_TICKS = 8;

	public static void hauntedBellCreatesPulse(PlayerEntity player) {
//		if (event.phase != TickEvent.Phase.END)
//			return;
		if (player.world.isRemote())
			return;
		if (player.isSpectator())
			return;

		if (player.world.getGameTime() % RECHARGE_TICKS != 0)
			return;

		if (player.isHolding(AllBlocks.HAUNTED_BELL::is))
			sendPulse(player.world, player.getBlockPos(), DISTANCE, false);
	}

	public static void sendPulse(World world, BlockPos pos, int distance, boolean canOverlap) {
		Chunk chunk = world.getChunkAt(pos);
		AllPackets.channel.sendToClientsTracking(new SoulPulseEffectPacket(pos, distance, canOverlap), (ServerWorld) chunk.getWorld(), chunk.getPos());
	}

}
