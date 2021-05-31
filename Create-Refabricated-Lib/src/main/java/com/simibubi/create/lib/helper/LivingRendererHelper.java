package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.LivingRendererAccessor;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class LivingRendererHelper {
	public static boolean addRenderer(LivingRenderer renderer, LayerRenderer toAdd) {
		return ((LivingRendererAccessor) renderer).create$addLayer(toAdd);
	}
}
