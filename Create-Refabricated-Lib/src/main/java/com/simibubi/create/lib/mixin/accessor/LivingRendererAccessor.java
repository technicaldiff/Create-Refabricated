package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

@Mixin(LivingRenderer.class)
public interface LivingRendererAccessor {
	@Invoker("addLayer")
	public boolean addLayer(LayerRenderer layerRenderer);
}
