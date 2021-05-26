package com.simibubi.create.content.logistics.item;

import com.simibubi.create.AllItems;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;

public abstract class LinkedControllerPacketBase implements C2SPacket {

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server.execute(() -> {
//				ServerPlayerEntity player = context.get()
//					.getSender();
				if (player == null)
					return;

				ItemStack heldItem = player.getHeldItemMainhand();
				if (!AllItems.LINKED_CONTROLLER.isIn(heldItem)) {
					heldItem = player.getHeldItemOffhand();
					if (!AllItems.LINKED_CONTROLLER.isIn(heldItem))
						return;
				}
				handle(player, heldItem);
			});
//		context.get()
//			.setPacketHandled(true);
	}

	protected abstract void handle(ServerPlayerEntity player, ItemStack heldItem);

}
