package com.simibubi.create.content.curiosities.tools;

import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.render.model.BakedModel;

public class ExtendoGripModel extends CustomRenderedItemModel {

	public ExtendoGripModel(BakedModel template) {
		super(template, "extendo_grip");
		addPartials("cog", "thin_short", "wide_short", "thin_long", "wide_long");
	}

	@Override
	public DynamicItemRenderer createRenderer() {
		return new ExtendoGripItemRenderer();
	}

}
