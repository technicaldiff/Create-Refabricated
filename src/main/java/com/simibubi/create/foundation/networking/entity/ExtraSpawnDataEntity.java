package com.simibubi.create.foundation.networking.entity;

import net.minecraft.network.PacketByteBuf;

public interface ExtraSpawnDataEntity {
	void readSpawnData(PacketByteBuf buf);

	void writeSpawnData(PacketByteBuf buf);
}
