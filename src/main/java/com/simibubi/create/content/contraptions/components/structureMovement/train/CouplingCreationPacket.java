package com.simibubi.create.content.contraptions.components.structureMovement.train;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;

public class CouplingCreationPacket implements C2SPacket {

	int id1, id2;

	protected CouplingCreationPacket() {}

	public CouplingCreationPacket(AbstractMinecartEntity cart1, AbstractMinecartEntity cart2) {
		id1 = cart1.getEntityId();
		id2 = cart2.getEntityId();
	}

	public void read(PacketBuffer buffer) {
		id1 = buffer.readInt();
		id2 = buffer.readInt();
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeInt(id1);
		buffer.writeInt(id2);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity sender, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server
			.execute(() -> {
				if (sender != null)
					CouplingHandler.tryToCoupleCarts(sender, sender.world, id1, id2);
			});
	}

}