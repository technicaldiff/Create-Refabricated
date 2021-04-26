package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface LivingEntityKnockbackStrengthCallback {
	public static final Event<LivingEntityKnockbackStrengthCallback> EVENT =
			EventFactory.createArrayBacked(LivingEntityKnockbackStrengthCallback.class, callbacks -> (strength, player) -> {
				for (LivingEntityKnockbackStrengthCallback callback : callbacks) {
					return callback.onLivingEntityTakeKnockback(strength, player);
				}

				return 0;
			});

	float onLivingEntityTakeKnockback(float strength, PlayerEntity player);
}
