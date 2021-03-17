package com.simibubi.create.content.curiosities.symmetry.client;

import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.render.CustomItemRenderer;

import net.minecraft.client.render.model.BakedModel;

public class SymmetryWandModel extends CustomRenderedItemModel {
	public SymmetryWandModel(BakedModel template) {
		super(template, "wand_of_symmetry");
		addPartials("bits", "core", "core_glow");
	}

	@Override
	public CustomItemRenderer createRenderer() {
		return new SymmetryWandItemRenderer();
	}
}
