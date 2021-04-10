package com.simibubi.create.foundation.networking;

import com.simibubi.create.foundation.tileEntity.SyncedTileEntity;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityConfigurationPacket<TE extends SyncedTileEntity> implements C2SPacket {

	protected BlockPos pos;

	protected TileEntityConfigurationPacket() {}

	public void read(PacketBuffer buffer) {
		pos = buffer.readBlockPos();
		readSettings(buffer);
	}

	public TileEntityConfigurationPacket(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeBlockPos(pos);
		writeSettings(buffer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server
			.execute(() -> {
				if (player == null)
					return;
				World world = player.world;

				if (world == null || !world.isBlockPresent(pos))
					return;
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity instanceof SyncedTileEntity) {
					applySettings((TE) tileEntity);
					((SyncedTileEntity) tileEntity).sendData();
					tileEntity.markDirty();
				}
			});

	}

	protected abstract void writeSettings(PacketBuffer buffer);

	protected abstract void readSettings(PacketBuffer buffer);

	protected abstract void applySettings(TE te);

}
