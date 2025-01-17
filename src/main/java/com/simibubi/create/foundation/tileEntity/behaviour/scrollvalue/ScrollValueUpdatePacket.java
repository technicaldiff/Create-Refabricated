package com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue;

import com.simibubi.create.foundation.networking.TileEntityConfigurationPacket;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class ScrollValueUpdatePacket extends TileEntityConfigurationPacket<SmartTileEntity> {

	int value;

	protected ScrollValueUpdatePacket() {}

	public ScrollValueUpdatePacket(BlockPos pos, int amount) {
		super(pos);
		this.value = amount;
	}

	@Override
	protected void writeSettings(PacketBuffer buffer) {
		buffer.writeInt(value);
	}

	@Override
	protected void readSettings(PacketBuffer buffer) {
		value = buffer.readInt();
	}

	@Override
	protected void applySettings(SmartTileEntity te) {
		ScrollValueBehaviour behaviour = te.getBehaviour(ScrollValueBehaviour.TYPE);
		if (behaviour == null)
			return;
		behaviour.setValue(value);
	}

}
