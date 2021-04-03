// PORTED CREATE SOURCE

package com.simibubi.create.content.contraptions.wrench;

import net.minecraft.client.render.model.BakedModel;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;

import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;

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
