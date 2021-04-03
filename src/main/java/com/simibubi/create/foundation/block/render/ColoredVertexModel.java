// PORTED CREATE SOURCE

package com.simibubi.create.foundation.block.render;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;

import com.simibubi.create.foundation.block.BlockVertexColorProvider;

public class ColoredVertexModel extends ForwardingBakedModel {
	private BlockVertexColorProvider colorProvider;

	public ColoredVertexModel(BakedModel originalModel, BlockVertexColorProvider colorProvider) {
		this.wrapped = originalModel;
		this.colorProvider = colorProvider;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
		context.pushTransform(quad -> {
			for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
				int color = this.colorProvider.getColor(quad.x(vertexIndex) + pos.getX(), quad.y(vertexIndex) + pos.getY(), quad.z(vertexIndex) + pos.getZ());
				quad.spriteColor(vertexIndex, 0, color);
			}
			return true;
		});
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}
}
