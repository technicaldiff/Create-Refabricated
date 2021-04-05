package com.simibubi.create.foundation.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.simibubi.create.CreateClient;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.fml.DistExecutor;

public class ClearBufferCacheCommand {

	static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("clearRenderBuffers").requires(cs -> cs.hasPermissionLevel(0)).executes(ctx -> {
			DistExecutor.unsafeRunWhenOn(EnvType.CLIENT, () -> ClearBufferCacheCommand::execute);
			ctx.getSource().sendFeedback(new StringTextComponent("Cleared rendering buffers."), true);
			return 1;
		});
	}

	@Environment(EnvType.CLIENT)
	private static void execute() {
		CreateClient.invalidateRenderers();
	}
}
