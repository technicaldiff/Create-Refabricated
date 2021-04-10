package com.simibubi.create.lib.entity;

import net.minecraft.network.PacketBuffer;

public interface ExtraSpawnDataEntity {
	void readSpawnData(PacketBuffer buf);

	void writeSpawnData(PacketBuffer buf);
}
