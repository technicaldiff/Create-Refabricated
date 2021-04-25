package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface LivingEntityTickCallback {
	public static final Event<LivingEntityTickCallback> EVENT = EventFactory.createArrayBacked(LivingEntityTickCallback.class, callbacks -> (entity) -> {
		for (LivingEntityTickCallback callback : callbacks) {
			callback.onLivingEntityTick(entity);
		}
	});

	void onLivingEntityTick(LivingEntity entity);
}
