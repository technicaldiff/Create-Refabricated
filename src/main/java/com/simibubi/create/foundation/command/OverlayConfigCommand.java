package com.simibubi.create.foundation.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.simibubi.create.foundation.networking.AllPackets;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.fabricmc.api.EnvType;

public class OverlayConfigCommand {

	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("overlay")
			.requires(cs -> cs.hasPermissionLevel(0))
			.then(Commands.literal("reset")
				.executes(ctx -> {
					DistExecutor.unsafeRunWhenOn(EnvType.CLIENT,
						() -> () -> ConfigureConfigPacket.Actions.overlayReset.performAction(""));

					DistExecutor.unsafeRunWhenOn(EnvType.SERVER,
						() -> () -> AllPackets.channel.send(
							PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) ctx.getSource()
								.getEntity()),
							new ConfigureConfigPacket(ConfigureConfigPacket.Actions.overlayReset.name(), "")));

					ctx.getSource()
						.sendFeedback(new StringTextComponent("reset overlay offset"), true);

					return 1;
				}))
			.executes(ctx -> {
				DistExecutor.unsafeRunWhenOn(EnvType.CLIENT,
					() -> () -> ConfigureConfigPacket.Actions.overlayScreen.performAction(""));

				DistExecutor.unsafeRunWhenOn(EnvType.SERVER,
					() -> () -> AllPackets.channel.send(
						PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) ctx.getSource()
							.getEntity()),
						new ConfigureConfigPacket(ConfigureConfigPacket.Actions.overlayScreen.name(), "")));

				ctx.getSource()
					.sendFeedback(new StringTextComponent("window opened"), true);

				return 1;
			});

	}
}
