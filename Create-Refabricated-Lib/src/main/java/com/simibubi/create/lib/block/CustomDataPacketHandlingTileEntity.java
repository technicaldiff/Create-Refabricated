package com.simibubi.create.lib.block;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;

public interface CustomDataPacketHandlingTileEntity {
	void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt);
}
