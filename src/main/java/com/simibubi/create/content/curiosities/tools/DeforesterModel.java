// PORTED CREATE SOURCE

package com.simibubi.create.content.curiosities.tools;

import net.minecraft.client.render.model.BakedModel;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;

import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;

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
