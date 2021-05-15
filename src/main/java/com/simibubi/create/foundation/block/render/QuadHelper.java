package com.simibubi.create.foundation.block.render;

import java.util.Arrays;

import com.simibubi.create.lib.helper.BakedQuadHelper;

import net.minecraft.client.renderer.model.BakedQuad;

public final class QuadHelper {

	private QuadHelper() {}

	public static BakedQuad clone(BakedQuad quad) {
		return new BakedQuad(Arrays.copyOf(quad.getVertexData(), quad.getVertexData().length),
			quad.getTintIndex(), quad.getFace(), BakedQuadHelper.getSprite(quad), quad.hasShade());
	}

}
