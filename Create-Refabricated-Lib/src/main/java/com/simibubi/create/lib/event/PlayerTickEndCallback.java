package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerTickEndCallback {
	public static final Event<PlayerTickEndCallback> EVENT = EventFactory.createArrayBacked(PlayerTickEndCallback.class, callbacks -> (player) -> {
		for (PlayerTickEndCallback callback : callbacks) {
			callback.onEndOfPlayerTick(player);
		}
	});

	void onEndOfPlayerTick(PlayerEntity player);
}
