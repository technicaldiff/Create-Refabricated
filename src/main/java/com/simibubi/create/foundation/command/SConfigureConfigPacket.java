package com.simibubi.create.foundation.command;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.goggles.GoggleConfigScreen;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import com.simibubi.create.foundation.config.ui.ConfigHelper;
import com.simibubi.create.foundation.gui.ScreenOpener;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderUI;
import com.simibubi.create.foundation.ponder.content.PonderIndexScreen;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SConfigureConfigPacket implements S2CPacket {

	private String option;
	private String value;

	protected SConfigureConfigPacket() {}

	public SConfigureConfigPacket(String option, String value) {
		this.option = option;
		this.value = value;
	}

	public SConfigureConfigPacket(PacketBuffer buffer) {
		this.option = buffer.readString(32767);
		this.value = buffer.readString(32767);
	}

	@Override
	public void read(PacketBuffer buf) {
		this.option = buf.readString(32767);
		this.value = buf.readString(32767);
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeString(option);
		buffer.writeString(value);
	}

	@Override
	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client
			.execute(() -> {
				if (option.startsWith("SET")) {
					trySetConfig(option.substring(3), value);
					return;
				}

				try {
					Actions.valueOf(option)
						.performAction(value);
				} catch (IllegalArgumentException e) {
					LogManager.getLogger()
						.warn("Received ConfigureConfigPacket with invalid Option: " + option);
				}
			});

	}

	private static void trySetConfig(String option, String value) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		if (player == null)
			return;

		ConfigHelper.ConfigPath configPath;
		try {
			configPath = ConfigHelper.ConfigPath.parse(option);
		} catch (IllegalArgumentException e) {
			player.sendStatusMessage(new StringTextComponent(e.getMessage()), false);
			return;
		}

//		if (configPath.getType() != ModConfig.Type.CLIENT) {
//			Create.LOGGER.warn("Received type-mismatched config packet on client");
//			return;
//		}

		try {
//			ConfigHelper.setConfigValue(configPath, value);
			player.sendStatusMessage(new StringTextComponent("Great Success!"), false);
//		} catch (ConfigHelper.InvalidValueException e) {
			player.sendStatusMessage(new StringTextComponent("Config could not be set the the specified value!"), false);
		} catch (Exception e) {
			player.sendStatusMessage(new StringTextComponent("Something went wrong while trying to set config value. Check the client logs for more information"), false);
			Create.LOGGER.warn("Exception during client-side config value set:", e);
		}

	}

	public enum Actions {
		configScreen(() -> Actions::configScreen),
		rainbowDebug(() -> Actions::rainbowDebug),
		overlayScreen(() -> Actions::overlayScreen),
		fixLighting(() -> Actions::experimentalLighting),
		overlayReset(() -> Actions::overlayReset),
		openPonder(() -> Actions::openPonder),
		fabulousWarning(() -> Actions::fabulousWarning)

		;

		private final Supplier<Consumer<String>> consumer;

		Actions(Supplier<Consumer<String>> action) {
			this.consumer = action;
		}

		void performAction(String value) {
			consumer.get()
				.accept(value);
		}

		@Environment(EnvType.CLIENT)
		private static void configScreen(String value) {
			if (value.equals("")) {
				ScreenOpener.open(BaseConfigScreen.forCreate(null));
				return;
			}

			ClientPlayerEntity player = Minecraft.getInstance().player;
			ConfigHelper.ConfigPath configPath;
			try {
				 configPath = ConfigHelper.ConfigPath.parse(value);
			} catch (IllegalArgumentException e) {
				player.sendStatusMessage(new StringTextComponent(e.getMessage()), false);
				return;
			}

			try {
//				ScreenOpener.open(SubMenuConfigScreen.find(configPath));
			} catch (Exception e) {
				player.sendStatusMessage(new StringTextComponent("Unable to find the specified config"), false);
			}
		}

		@Environment(EnvType.CLIENT)
		private static void rainbowDebug(String value) {
			ClientPlayerEntity player = Minecraft.getInstance().player;
			if (player == null || "".equals(value))
				return;

			if (value.equals("info")) {
				ITextComponent text = new StringTextComponent("Rainbow Debug Utility is currently: ")
					.append(boolToText(AllConfigs.CLIENT.rainbowDebug.get()));
				player.sendStatusMessage(text, false);
				return;
			}

			AllConfigs.CLIENT.rainbowDebug.set(Boolean.parseBoolean(value));
			ITextComponent text = boolToText(AllConfigs.CLIENT.rainbowDebug.get())
				.append(new StringTextComponent(" Rainbow Debug Utility").formatted(TextFormatting.WHITE));
			player.sendStatusMessage(text, false);
		}

		@Environment(EnvType.CLIENT)
		private static void overlayReset(String value) {
			AllConfigs.CLIENT.overlayOffsetX.set(0);
			AllConfigs.CLIENT.overlayOffsetY.set(0);
		}

		@Environment(EnvType.CLIENT)
		private static void overlayScreen(String value) {
			ScreenOpener.open(new GoggleConfigScreen());
		}

		@Environment(EnvType.CLIENT)
		private static void experimentalLighting(String value) {
//			ForgeConfig.CLIENT.experimentalForgeLightPipelineEnabled.set(true);
			Minecraft.getInstance().worldRenderer.loadRenderers();
		}

		@Environment(EnvType.CLIENT)
		private static void openPonder(String value) {
			if (value.equals("index")) {
				ScreenOpener.transitionTo(new PonderIndexScreen());
				return;
			}

			ResourceLocation id = new ResourceLocation(value);
			if (!PonderRegistry.all.containsKey(id)) {
				Create.LOGGER.error("Could not find ponder scenes for item: " + id);
				return;
			}

			ScreenOpener.transitionTo(PonderUI.of(id));

		}

		@Environment(EnvType.CLIENT)
		private static void fabulousWarning(String value) {
			AllConfigs.CLIENT.ignoreFabulousWarning.set(true);
			Minecraft.getInstance().ingameGUI.addChatMessage(ChatType.CHAT,
				new StringTextComponent("Disabled Fabulous graphics warning"),
				Minecraft.getInstance().player.getUniqueID());
		}

		private static IFormattableTextComponent boolToText(boolean b) {
			return b ? new StringTextComponent("enabled").formatted(TextFormatting.DARK_GREEN)
				: new StringTextComponent("disabled").formatted(TextFormatting.RED);
		}
	}
}
