// PORTED CREATE SOURCE

package com.simibubi.create.content.contraptions.components.structureMovement.gantry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;

public class GantryContraptionUpdatePacket implements S2CPacket {

	int entityID;
	double coord;
	double motion;

	public GantryContraptionUpdatePacket(int entityID, double coord, double motion) {
		this.entityID = entityID;
		this.coord = coord;
		this.motion = motion;
	}

	@Override
	public void read(PacketByteBuf buffer) {
		entityID = buffer.readInt();
		coord = buffer.readFloat();
		motion = buffer.readFloat();
	}

	@Override
	public void write(PacketByteBuf buffer) {
		buffer.writeInt(entityID);
		buffer.writeFloat((float) coord);
		buffer.writeFloat((float) motion);
	}

	@Override
	public void handle(MinecraftClient client, ClientPlayNetworkHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		client.execute(() -> GantryContraptionEntity.handlePacket(this));
	}
}
