package com.simibubi.create.content.contraptions.goggles;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.minecraft.client.renderer.model.IBakedModel;

public class GogglesModel extends ForwardingBakedModel {

	public GogglesModel(IBakedModel template) {
		wrapped = template;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

}
