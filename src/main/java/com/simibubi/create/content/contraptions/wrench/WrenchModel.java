package com.simibubi.create.content.contraptions.wrench;

import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.render.CustomItemRenderer;

import net.minecraft.client.render.model.BakedModel;

public class WrenchModel extends CustomRenderedItemModel {
	public WrenchModel(BakedModel template) {
		super(template, "wrench");
		addPartials("gear");
	}

	@Override
	public CustomItemRenderer createRenderer() {
		return new WrenchItemRenderer();
	}
}
