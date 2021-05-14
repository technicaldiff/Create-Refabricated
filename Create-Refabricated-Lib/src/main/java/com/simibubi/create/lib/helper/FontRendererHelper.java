package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.FontRendererAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.util.ResourceLocation;

@Environment(EnvType.CLIENT)
public final class FontRendererHelper {

	public static Font getFontStorage(FontRenderer renderer, ResourceLocation location) {
		return get(renderer).create$getFontStorage(location);
	}

	private static FontRendererAccessor get(FontRenderer renderer) {
		return MixinHelper.cast(renderer);
	}

	private FontRendererHelper() {}
}
