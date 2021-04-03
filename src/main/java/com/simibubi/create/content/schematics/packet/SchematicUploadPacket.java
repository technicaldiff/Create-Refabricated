// PORTED CREATE SOURCE

package com.simibubi.create.content.schematics.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;

public class SchematicUploadPacket implements C2SPacket {

	public static final int BEGIN = 0;
	public static final int WRITE = 1;
	public static final int FINISH = 2;

	private int code;
	private long size;
	private String schematic;
	private byte[] data;

	public SchematicUploadPacket(int code, String schematic) {
		this.code = code;
		this.schematic = schematic;
	}

	public static SchematicUploadPacket begin(String schematic, long size) {
		SchematicUploadPacket pkt = new SchematicUploadPacket(BEGIN, schematic);
		pkt.size = size;
		return pkt;
	}

	public static SchematicUploadPacket write(String schematic, byte[] data) {
		SchematicUploadPacket pkt = new SchematicUploadPacket(WRITE, schematic);
		pkt.data = data;
		return pkt;
	}

	public static SchematicUploadPacket finish(String schematic) {
		return new SchematicUploadPacket(FINISH, schematic);
	}

	@Override
	public void read(PacketByteBuf buffer) {
		code = buffer.readInt();
		schematic = buffer.readString(256);

		if (code == BEGIN)
			size = buffer.readLong();
		if (code == WRITE)
			data = buffer.readByteArray();
	}

	public void write(PacketByteBuf buffer) {
		buffer.writeInt(code);
		buffer.writeString(schematic);

		if (code == BEGIN)
			buffer.writeLong(size);
		if (code == WRITE)
			buffer.writeByteArray(data);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null)
				return;
			/*if (code == BEGIN) {
				BlockPos pos = ((SchematicTableContainer) player.currentScreenHandler).getTileEntity()
					.getPos();
				Create.schematicReceiver.handleNewUpload(player, schematic, size, pos);
			}
			if (code == WRITE)
				Create.schematicReceiver.handleWriteRequest(player, schematic, data);
			if (code == FINISH)
				Create.schematicReceiver.handleFinishedUpload(player, schematic);*/
		});
	}

}
