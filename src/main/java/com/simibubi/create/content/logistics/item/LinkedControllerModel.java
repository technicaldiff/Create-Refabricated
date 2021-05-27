package com.simibubi.create.content.logistics.item;

import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.model.IBakedModel;

public class LinkedControllerModel extends CustomRenderedItemModel {

	public LinkedControllerModel(IBakedModel template) {
		super(template, "linked_controller");
		addPartials("powered", "button");
	}

	@Override
	public BuiltinItemRendererRegistry.DynamicItemRenderer createRenderer() {
		return new LinkedControllerItemRenderer();
	}

}
