package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface LivingEntityExperienceDropCallback {
	public static final Event<LivingEntityExperienceDropCallback> EVENT = EventFactory.createArrayBacked(LivingEntityExperienceDropCallback.class, callbacks -> (i, player) -> {
		for (LivingEntityExperienceDropCallback callback : callbacks) {
			return callback.onLivingEntityExperienceDrop(i, player);
		}

		return 0;
	});

	int onLivingEntityExperienceDrop(int i, PlayerEntity player);
}
