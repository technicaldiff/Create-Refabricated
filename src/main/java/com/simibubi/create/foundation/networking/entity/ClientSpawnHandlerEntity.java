package com.simibubi.create.foundation.networking.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

@Environment(EnvType.CLIENT)
public interface ClientSpawnHandlerEntity {
	public void onClientSpawn(EntitySpawnS2CPacket packet);
}
