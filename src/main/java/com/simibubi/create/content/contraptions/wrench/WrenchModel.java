package com.simibubi.create.content.contraptions.wrench;

import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.render.model.BakedModel;

public class WrenchModel extends CustomRenderedItemModel {
	public WrenchModel(BakedModel template) {
		super(template, "wrench");
		addPartials("gear");
	}

	@Override
	public DynamicItemRenderer createRenderer() {
		return new WrenchItemRenderer();
	}
}
