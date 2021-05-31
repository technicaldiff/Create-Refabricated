package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface OnPlayerRendererInitCallback {
	public static final Event<OnPlayerRendererInitCallback> EVENT = EventFactory.createArrayBacked(OnPlayerRendererInitCallback.class, callbacks -> () -> {
		for (OnPlayerRendererInitCallback callback : callbacks) {
			callback.register();
		}
	});

	void register();
}
