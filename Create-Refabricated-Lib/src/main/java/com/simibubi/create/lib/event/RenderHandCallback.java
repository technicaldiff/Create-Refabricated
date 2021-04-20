package com.simibubi.create.lib.event;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface RenderHandCallback {
	public static final Event<RenderHandCallback> EVENT = EventFactory.createArrayBacked(RenderHandCallback.class, callbacks -> (player, hand, stack, matrices, vertexConsumers, tickDelta, pitch, swingProgress, equipProgress, light) -> {
		boolean cancelled = false;
		for (RenderHandCallback callback : callbacks) {
			if (callback.onRenderHand(player, hand, stack, matrices, vertexConsumers, tickDelta, pitch, swingProgress, equipProgress, light)) {
				cancelled = true;
			}
		}
		return cancelled;
	});

	boolean onRenderHand(AbstractClientPlayerEntity player, Hand hand, ItemStack stack, MatrixStack matrices, IRenderTypeBuffer vertexConsumers, float tickDelta, float pitch, float swingProgress, float equipProgress, int light);
}
