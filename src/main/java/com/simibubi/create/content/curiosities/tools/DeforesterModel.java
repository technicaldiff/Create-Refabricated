package com.simibubi.create.content.curiosities.tools;

import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.render.model.BakedModel;

public class DeforesterModel extends CustomRenderedItemModel {
	public DeforesterModel(BakedModel template) {
		super(template, "deforester");
		addPartials("gear", "core", "core_glow");
	}

	@Override
	public DynamicItemRenderer createRenderer() {
		return new DeforesterItemRenderer();
	}
}
