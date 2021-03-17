package com.simibubi.create.content.schematics.packet;

import com.simibubi.create.Create;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class InstantSchematicPacket implements C2SPacket {

	private String name;
	private BlockPos origin;
	private BlockPos bounds;

	public InstantSchematicPacket(String name, BlockPos origin, BlockPos bounds) {
		this.name = name;
		this.origin = origin;
		this.bounds = bounds;
	}

	@Override
	public void read(PacketByteBuf buffer) {
		name = buffer.readString(32767);
		origin = buffer.readBlockPos();
		bounds = buffer.readBlockPos();
	}

	@Override
	public void write(PacketByteBuf buffer) {
		buffer.writeString(name);
		buffer.writeBlockPos(origin);
		buffer.writeBlockPos(bounds);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null)
				return;
			//Create.schematicReceiver.handleInstantSchematic(player, name, player.world, origin, bounds);
		});
	}

}
