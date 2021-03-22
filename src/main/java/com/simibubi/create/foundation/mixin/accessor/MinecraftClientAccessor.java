package com.simibubi.create.foundation.mixin.accessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
	@Accessor("pausedTickDelta")
	float getPausedTickDelta();

	@Accessor("itemColors")
	ItemColors getItemColors();
}
