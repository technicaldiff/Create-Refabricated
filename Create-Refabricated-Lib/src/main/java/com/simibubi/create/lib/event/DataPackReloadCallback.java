package com.simibubi.create.lib.event;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.IFutureReloadListener;

public interface DataPackReloadCallback {
	public static final Event<DataPackReloadCallback> EVENT = EventFactory.createArrayBacked(DataPackReloadCallback.class, callbacks -> registry -> {
		List<IFutureReloadListener> listeners = new ArrayList<>();
		for (DataPackReloadCallback callback : callbacks) {
			listeners.addAll(callback.onDataPackReload(registry));
		}
		return listeners;
	});

	List<IFutureReloadListener> onDataPackReload(DataPackRegistries dataPackRegistries);
}
