package com.simibubi.create.lib.entity;

import net.minecraft.network.play.server.SSpawnObjectPacket;

public interface ClientSpawnHandlerEntity {
	public void onClientSpawn(SSpawnObjectPacket packet);
}
