package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResultType;

public interface StartRidingCallback {
	public static final Event<StartRidingCallback> EVENT = EventFactory.createArrayBacked(StartRidingCallback.class, callbacks -> (mounted, mounting) -> {
		ActionResultType result = ActionResultType.PASS;
		for (StartRidingCallback callback : callbacks) {
			result = callback.onStartRiding(mounted, mounting);
			if (result == ActionResultType.FAIL) {
				return result;
			}
		}
		return result;
	});

	ActionResultType onStartRiding(Entity mounted, Entity mounting);
}
