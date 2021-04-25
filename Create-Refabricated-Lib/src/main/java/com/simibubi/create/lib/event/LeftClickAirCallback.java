package com.simibubi.create.lib.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.entity.player.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public interface LeftClickAirCallback {
	public static final Event<LeftClickAirCallback> EVENT = EventFactory.createArrayBacked(LeftClickAirCallback.class, callbacks -> (player) -> {
		for (LeftClickAirCallback callback : callbacks) {
			callback.onLeftClickAir(player);
		}
	});

	void onLeftClickAir(ClientPlayerEntity player);
}
