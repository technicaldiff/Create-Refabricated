package com.simibubi.create.lib.networking.entity;

import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ClientSpawnHandlerEntity {
	public void onClientSpawn(EntitySpawnS2CPacket packet);
}
