package com.simibubi.create.foundation.command;

import com.simibubi.create.foundation.networking.AllPackets;

import net.minecraft.entity.player.ServerPlayerEntity;

public class ToggleExperimentalRenderingCommand extends ConfigureConfigCommand {

	public ToggleExperimentalRenderingCommand() {
		super("experimentalRendering");
	}

	@Override
	protected void sendPacket(ServerPlayerEntity player, String option) {
		AllPackets.channel.send(PacketDistributor.PLAYER.with(() -> player),
			new ConfigureConfigPacket(ConfigureConfigPacket.Actions.experimentalRendering.name(), option));
	}
}
