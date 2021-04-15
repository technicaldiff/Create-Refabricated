package com.simibubi.create.lib.entity;

import net.minecraft.network.play.server.SSpawnObjectPacket;

public interface ClientSpawnHandlerEntity {
	void onClientSpawn(SSpawnObjectPacket packet);
}
