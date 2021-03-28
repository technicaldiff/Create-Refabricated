package com.simibubi.create;

import java.util.Random;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.SharedConstants;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

import org.spongepowered.asm.mixin.MixinEnvironment;

import com.simibubi.create.content.contraptions.TorquePropagator;
import com.simibubi.create.content.logistics.RedstoneLinkNetworkHandler;
import com.simibubi.create.content.palettes.AllPaletteBlocks;
import com.simibubi.create.events.CommonEvents;
import com.simibubi.create.foundation.advancement.AllTriggers;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.resource.TranslationsHolder;
import com.simibubi.create.foundation.worldgen.AllWorldFeatures;

public class Create implements ModInitializer {
	public static final String ID = "create";
	public static final String NAME = "Create";

	public static Logger logger = LogManager.getLogger();
	public static ItemGroup baseCreativeTab = FabricItemGroupBuilder.build(id("base"), () -> new ItemStack(AllBlocks.COGWHEEL));
	public static ItemGroup palettesCreativeTab = FabricItemGroupBuilder.build(id("palettes"), () -> new ItemStack(AllPaletteBlocks.ORNATE_IRON_WINDOW));

	public static RedstoneLinkNetworkHandler redstoneLinkNetworkHandler;
	public static TorquePropagator torquePropagator;
	public static Random random;

	@Override
	public void onInitialize() {
		AllBlocks.register();
		AllItems.register();
		AllFluids.register();
		AllTags.register();
		AllPaletteBlocks.register();
		AllEntityTypes.register();
		AllBlockEntities.register();
		AllMovementBehaviours.register();
		AllConfigs.register();

		AllSoundEvents.register();

		random = new Random();

		redstoneLinkNetworkHandler = new RedstoneLinkNetworkHandler();
		torquePropagator = new TorquePropagator();

		AllPackets.registerPackets();

		CommonEvents.register();

		AllWorldFeatures.reload();

		TranslationsHolder.initialize();

		if (SharedConstants.isDevelopment) MixinEnvironment.getCurrentEnvironment().audit();

		AllTriggers.register();
	}

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}

	public static AllConfigs getConfig() {
		AllConfigs config = AutoConfig.getConfigHolder(AllConfigs.class).getConfig();
		try {
			config.validatePostLoad(); // The best way to validate :)
		} catch (ConfigData.ValidationException e) {
			throw new RuntimeException(e);
		}
		return config;
	}
}
