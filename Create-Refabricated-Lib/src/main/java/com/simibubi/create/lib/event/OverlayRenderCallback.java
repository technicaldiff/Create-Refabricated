package com.simibubi.create.lib.event;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MainWindow;

public interface OverlayRenderCallback {
	public static final Event<OverlayRenderCallback> EVENT = EventFactory.createArrayBacked(OverlayRenderCallback.class, callbacks -> (stack, partialTicks, window, type) -> {
		for (OverlayRenderCallback callback : callbacks) {
			callback.onOverlayRender(stack, partialTicks, window, type);
		}
	});

	void onOverlayRender(MatrixStack stack, float partialTicks, MainWindow window, Types type);

	enum Types {
		AIR,
		CROSSHAIRS
		;
	}
}
