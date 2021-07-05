package com.simibubi.create.content.schematics.packet;

import com.simibubi.create.Create;
import com.simibubi.create.content.schematics.block.SchematicTableContainer;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class SchematicUploadPacket implements C2SPacket {

	public static final int BEGIN = 0;
	public static final int WRITE = 1;
	public static final int FINISH = 2;

	private int code;
	private long size;
	private String schematic;
	private byte[] data;

	protected SchematicUploadPacket() {}

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

	public void read(PacketBuffer buffer) {
		code = buffer.readInt();
		schematic = buffer.readString(256);

		if (code == BEGIN)
			size = buffer.readLong();
		if (code == WRITE)
			data = buffer.readByteArray();
	}

	public void write(PacketBuffer buffer) {
		buffer.writeInt(code);
		buffer.writeString(schematic);

		if (code == BEGIN)
			buffer.writeLong(size);
		if (code == WRITE)
			buffer.writeByteArray(data);
	}

	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server
			.execute(() -> {
				if (player == null)
					return;
				if (code == BEGIN) {
					BlockPos pos = ((SchematicTableContainer) player.openContainer).getTileEntity()
							.getPos();
					Create.SCHEMATIC_RECEIVER.handleNewUpload(player, schematic, size, pos);
				}
				if (code == WRITE)
					Create.SCHEMATIC_RECEIVER.handleWriteRequest(player, schematic, data);
				if (code == FINISH)
					Create.SCHEMATIC_RECEIVER.handleFinishedUpload(player, schematic);
			});
	}

}
