package com.simibubi.create.lib.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public interface MouseButtonCallback {
	public static final Event<MouseButtonCallback> EVENT = EventFactory.createArrayBacked(MouseButtonCallback.class, callbacks -> (button, action, mods) -> {
		for (MouseButtonCallback callback : callbacks) {
			callback.onMouseButton(button, action, mods);
		}
	});

	void onMouseButton(int button, int action, int mods);
}
