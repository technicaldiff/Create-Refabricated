package com.simibubi.create.foundation.command;

import com.simibubi.create.foundation.networking.AllPackets;

import net.minecraft.entity.player.ServerPlayerEntity;

public class ToggleDebugCommand extends ConfigureConfigCommand {

	public ToggleDebugCommand() {
		super("rainbowDebug");
	}

	@Override
	protected void sendPacket(ServerPlayerEntity player, String option) {
		AllPackets.channel.sendToClient(new SConfigureConfigPacket(SConfigureConfigPacket.Actions.rainbowDebug.name(), option), player);
//		AllPackets.channel.send(
//				PacketDistributor.PLAYER.with(() -> player),
//				new SConfigureConfigPacket(SConfigureConfigPacket.Actions.rainbowDebug.name(), option)
//		);
	}
}
