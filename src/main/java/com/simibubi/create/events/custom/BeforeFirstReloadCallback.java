package com.simibubi.create.events.custom;

import net.minecraft.client.MinecraftClient;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface BeforeFirstReloadCallback {
	public static final Event<BeforeFirstReloadCallback> EVENT = EventFactory.createArrayBacked(BeforeFirstReloadCallback.class, callbacks -> client -> {
		for (BeforeFirstReloadCallback callback : callbacks) {
			callback.beforeFirstReload(client);
		}
	});

	void beforeFirstReload(MinecraftClient client);
}
