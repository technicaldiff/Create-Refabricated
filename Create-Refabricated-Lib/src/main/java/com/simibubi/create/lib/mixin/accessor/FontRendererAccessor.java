package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.util.ResourceLocation;

@Environment(EnvType.CLIENT)
@Mixin(FontRenderer.class)
public interface FontRendererAccessor {
	@Invoker("getFontStorage")
	Font create$getFontStorage(ResourceLocation resourceLocation);
}
