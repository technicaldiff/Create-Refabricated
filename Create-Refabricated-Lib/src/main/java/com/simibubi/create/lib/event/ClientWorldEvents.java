package com.simibubi.create.lib.event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public final class ClientWorldEvents {
	public static final Event<Load> LOAD = EventFactory.createArrayBacked(Load.class, callbacks -> (client, world) -> {
		for (Load callback : callbacks) {
			callback.onWorldLoad(client, world);
		}
	});

	public static final Event<Unload> UNLOAD = EventFactory.createArrayBacked(Unload.class, callbacks -> (client, world) -> {
		for (Unload callback : callbacks) {
			callback.onWorldUnload(client, world);
		}
	});

	private ClientWorldEvents() {}

	@FunctionalInterface
	public interface Load {
		void onWorldLoad(MinecraftClient client, ClientWorld world);
	}

	@FunctionalInterface
	public interface Unload {
		void onWorldUnload(MinecraftClient client, ClientWorld world);
	}
}
