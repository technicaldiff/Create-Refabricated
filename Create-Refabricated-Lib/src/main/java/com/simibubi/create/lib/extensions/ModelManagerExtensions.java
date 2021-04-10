package com.simibubi.create.lib.extensions;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

public interface ModelManagerExtensions {
	IBakedModel create$getModel(ResourceLocation id);
}
