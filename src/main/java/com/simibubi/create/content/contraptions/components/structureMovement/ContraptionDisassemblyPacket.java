package com.simibubi.create.content.contraptions.components.structureMovement;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;

public class ContraptionDisassemblyPacket implements S2CPacket {

	int entityID;
	StructureTransform transform;

	protected ContraptionDisassemblyPacket() {}

	public ContraptionDisassemblyPacket(int entityID, StructureTransform transform) {
		this.entityID = entityID;
		this.transform = transform;
	}

	public void read(PacketBuffer buffer) {
		entityID = buffer.readInt();
		transform = StructureTransform.fromBuffer(buffer);
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeInt(entityID);
		transform.writeToBuffer(buffer);
	}

	@Override
	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client
			.execute(() ->
				AbstractContraptionEntity.handleDisassemblyPacket(this));
	}

}
