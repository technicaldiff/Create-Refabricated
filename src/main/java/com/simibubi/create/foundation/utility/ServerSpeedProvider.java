package com.simibubi.create.foundation.utility;

import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.gui.widgets.InterpolatedChasingValue;
import com.simibubi.create.foundation.networking.AllPackets;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;

public class ServerSpeedProvider {

	static int clientTimer = 0;
	static int serverTimer = 0;
	static boolean initialized = false;
	static InterpolatedChasingValue modifier = new InterpolatedChasingValue().withSpeed(.25f);

	public static void serverTick(MinecraftServer server) {
		serverTimer++;
		if (serverTimer > getSyncInterval()) {
			AllPackets.channel.sendToClientsInServer(new Packet(), server);
			serverTimer = 0;
		}
	}

	@Environment(EnvType.CLIENT)
	public static void clientTick() {
		if (Minecraft.getInstance()
			.isSingleplayer()
			&& Minecraft.getInstance()
				.isGamePaused())
			return;
		modifier.tick();
		clientTimer++;
	}

	public static Integer getSyncInterval() {
		return AllConfigs.SERVER.tickrateSyncTimer.get();
	}

	public static float get() {
		return modifier.value;
	}

	public static class Packet implements S2CPacket {

		public Packet() {}

		public void read(PacketBuffer buffer) {}

		@Override
		public void write(PacketBuffer buffer) {}

		@Override
		public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
			client
				.execute(() -> {
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
