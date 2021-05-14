package com.simibubi.create.lib.mixin.accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.settings.KeyBinding;

import net.minecraft.client.util.InputMappings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
	@Accessor("keyCode")
	InputMappings.Input create$keyCode();
}
