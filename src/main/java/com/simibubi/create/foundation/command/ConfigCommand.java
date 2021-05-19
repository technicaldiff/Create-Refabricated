package com.simibubi.create.foundation.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.simibubi.create.foundation.networking.AllPackets;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ConfigCommand {

	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("config")
				.executes(ctx -> {
					ServerPlayerEntity player = ctx.getSource().asPlayer();
					AllPackets.channel.sendToClient(new SConfigureConfigPacket(SConfigureConfigPacket.Actions.configScreen.name(), ""), player);
//					AllPackets.channel.send(
//							PacketDistributor.PLAYER.with(() -> player),
//							new SConfigureConfigPacket(SConfigureConfigPacket.Actions.configScreen.name(), "")
//					);

					return Command.SINGLE_SUCCESS;
				});
	}

}
