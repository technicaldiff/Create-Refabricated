package com.simibubi.create.lib.entity;

import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

public interface ClientSpawnHandlerEntity {
	public void onClientSpawn(EntitySpawnS2CPacket packet);
}
