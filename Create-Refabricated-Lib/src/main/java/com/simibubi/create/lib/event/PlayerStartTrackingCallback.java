package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerStartTrackingCallback {
	public static final Event<PlayerStartTrackingCallback> EVENT = EventFactory.createArrayBacked(PlayerStartTrackingCallback.class, callbacks -> (player, target) -> {
		for (PlayerStartTrackingCallback callback : callbacks) {
			callback.onPlayerStartTracking(player, target);
		}
	});

	void onPlayerStartTracking(PlayerEntity player, Entity target);
}
