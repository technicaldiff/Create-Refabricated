package com.simibubi.create.lib.event;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public interface RenderWorldLastCallback {
	public static final Event<RenderWorldLastCallback> EVENT = EventFactory.createArrayBacked(RenderWorldLastCallback.class, callbacks -> (ms) -> {
		for (RenderWorldLastCallback callback : callbacks) {
			callback.onRenderWorldLast(ms);
		}
	});

	void onRenderWorldLast(MatrixStack ms);
}
