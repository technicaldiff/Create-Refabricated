package com.simibubi.create.lib.event;

import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public interface ModelsBakedCallback {
	public static final Event<ModelsBakedCallback> EVENT = EventFactory.createArrayBacked(ModelsBakedCallback.class, callbacks -> (manager, models, loader) -> {
		for (ModelsBakedCallback callback : callbacks) {
			callback.onModelsBaked(manager, models, loader);
		}
	});

	void onModelsBaked(BakedModelManager manager, Map<Identifier, BakedModel> models, ModelLoader loader);
}
