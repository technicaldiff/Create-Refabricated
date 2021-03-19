package com.simibubi.create.events.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;

public interface BeforeFirstReloadCallback {
	public static final Event<BeforeFirstReloadCallback> EVENT = EventFactory.createArrayBacked(BeforeFirstReloadCallback.class, callbacks -> client -> {
		for (BeforeFirstReloadCallback callback : callbacks) {
			callback.beforeFirstReload(client);
		}
	});

	void beforeFirstReload(MinecraftClient client);
}
