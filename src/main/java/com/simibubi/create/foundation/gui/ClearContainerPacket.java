package com.simibubi.create.foundation.gui;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;

public class ClearContainerPacket implements C2SPacket {

	public ClearContainerPacket() {}

	public ClearContainerPacket(PacketBuffer buffer) {}

	@Override
	public void read(PacketBuffer buf) {}

	@Override
	public void write(PacketBuffer buffer) {}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server.execute(() -> {
//				ServerPlayerEntity player = context.get()
//					.getSender();
				if (player == null)
					return;
				if (!(player.openContainer instanceof IClearableContainer))
					return;
				((IClearableContainer) player.openContainer).clearContents();
			});
//		context.get()
//			.setPacketHandled(true);
	}

}
