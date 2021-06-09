package com.simibubi.create.content.logistics.block.depot;

import com.simibubi.create.foundation.networking.TileEntityConfigurationPacket;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class EjectorTriggerPacket extends TileEntityConfigurationPacket<EjectorTileEntity> {

	protected EjectorTriggerPacket() {}

	public EjectorTriggerPacket(BlockPos pos) {
		super(pos);
	}

	@Override
	protected void writeSettings(PacketBuffer buffer) {}

	@Override
	protected void readSettings(PacketBuffer buffer) {}

	@Override
	protected void applySettings(EjectorTileEntity te) {
		te.activate();
	}

}
