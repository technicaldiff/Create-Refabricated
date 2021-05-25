package com.simibubi.create.lib.event;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface OverlayRenderCallback {
	public static final Event<OverlayRenderCallback> EVENT = EventFactory.createArrayBacked(OverlayRenderCallback.class, callbacks -> (stack, partialTicks, type) -> {
		for (OverlayRenderCallback callback : callbacks) {
			callback.onOverlayRender(stack, partialTicks, type);
		}
	});

	void onOverlayRender(MatrixStack stack, float partialTicks, Types type);

	enum Types {
		AIR,
		;
	}
}
