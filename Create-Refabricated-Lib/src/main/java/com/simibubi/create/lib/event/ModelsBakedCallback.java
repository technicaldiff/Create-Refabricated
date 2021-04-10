package com.simibubi.create.lib.event;

import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.util.ResourceLocation;

@Environment(EnvType.CLIENT)
public interface ModelsBakedCallback {
	public static final Event<ModelsBakedCallback> EVENT = EventFactory.createArrayBacked(ModelsBakedCallback.class, callbacks -> (manager, models, loader) -> {
		for (ModelsBakedCallback callback : callbacks) {
			callback.onModelsBaked(manager, models, loader);
		}
	});

	void onModelsBaked(ModelManager manager, Map<ResourceLocation, IBakedModel> models, ModelBakery loader);
}
