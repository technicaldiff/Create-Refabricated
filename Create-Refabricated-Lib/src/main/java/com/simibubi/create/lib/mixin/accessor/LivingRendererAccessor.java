package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

@Environment(EnvType.CLIENT)
@Mixin(LivingRenderer.class)
public interface LivingRendererAccessor {
	@Invoker("addLayer")
	boolean create$addLayer(LayerRenderer<?, ?> layerRenderer);
}
