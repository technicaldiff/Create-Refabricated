// PORTED CREATE SOURCE

package com.simibubi.create.foundation.utility;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.gui.widgets.InterpolatedChasingValue;
import com.simibubi.create.foundation.networking.AllPackets;

public class ServerSpeedProvider {
	private static int clientTimer = 0;
	private static int serverTimer = 0;
	private static boolean initialized = false;
	private static InterpolatedChasingValue modifier = new InterpolatedChasingValue().withSpeed(.25f);

	public static void serverTick(MinecraftServer server) {
		serverTimer++;
		if (serverTimer > getSyncInterval()) {
			AllPackets.CHANNEL.sendToClientsInServer(new Packet(), server);
			serverTimer = 0;
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static void clientTick() {
		if (MinecraftClient.getInstance().isIntegratedServerRunning() && MinecraftClient.getInstance().isPaused())
			return;
		modifier.tick();
		clientTimer++;
	}

	public static Integer getSyncInterval() {
		return Create.getConfig().server.tickrateSyncTimer;
	}

	public static float get() {
		return modifier.value;
	}

	public static class Packet implements S2CPacket {
		public Packet() {
		}

		@Override
		public void read(PacketByteBuf buf) {
		}

		@Override
		public void write(PacketByteBuf buffer) {
		}

		@Override
		public void handle(MinecraftClient client, ClientPlayNetworkHandler handler, ResponseTarget responseTarget) {
			client.execute(() -> {
				if (!initialized) {
					initialized = true;
					clientTimer = 0;
					return;
				}
				float target = ((float) getSyncInterval()) / Math.max(clientTimer, 1);
				modifier.target(Math.min(target, 1));
				clientTimer = 0;
			});
		}
	}
}
