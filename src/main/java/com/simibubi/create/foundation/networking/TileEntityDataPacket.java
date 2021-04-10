package com.simibubi.create.foundation.networking;

import com.simibubi.create.foundation.tileEntity.SyncedTileEntity;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * A server to client version of {@link TileEntityConfigurationPacket}
 * 
 * @param <TE>
 */
public abstract class TileEntityDataPacket<TE extends SyncedTileEntity> implements S2CPacket {

	protected BlockPos tilePos;

	protected TileEntityDataPacket() {}

	public void read(PacketBuffer buffer) {
		tilePos = buffer.readBlockPos();
	}

	public TileEntityDataPacket(BlockPos pos) {
		this.tilePos = pos;
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeBlockPos(tilePos);
		writeData(buffer);
	}

	@Override
	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client.execute(() -> {
			ClientWorld world = Minecraft.getInstance().world;

			if (world == null)
				return;

			TileEntity tile = world.getTileEntity(tilePos);

			if (tile instanceof SyncedTileEntity) {
				handlePacket((TE) tile);
			}
		});
	}

	protected abstract void writeData(PacketBuffer buffer);

	protected abstract void handlePacket(TE tile);
}
