package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

@Mixin(Screen.class)
public interface ScreenAccessor {
	@Accessor("client")
	Minecraft create$client();
}
