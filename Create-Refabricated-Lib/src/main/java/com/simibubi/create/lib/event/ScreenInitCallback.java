package com.simibubi.create.lib.event;

import java.util.List;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;

public interface ScreenInitCallback {
	public static final Event<ScreenInitCallback> EVENT = EventFactory.createArrayBacked(ScreenInitCallback.class, callbacks -> (screen, widgets, add, remove) -> {
		for (ScreenInitCallback callback : callbacks) {
			callback.onScreenInit(screen, widgets, add, remove);
		}
	});

	void onScreenInit(Screen gui, List<Widget> list, Consumer<Widget> add, Consumer<Widget> remove);
}
