package com.simibubi.create.foundation.block.render;

import java.util.Random;
import java.util.function.Supplier;

import com.simibubi.create.foundation.block.IBlockVertexColor;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockDisplayReader;

public class ColoredVertexModel extends ForwardingBakedModel {

	private IBlockVertexColor color;

	public ColoredVertexModel(IBakedModel originalModel, IBlockVertexColor color) {
		wrapped = originalModel;
		this.color = color;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(IBlockDisplayReader blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
		Vector3f vertexPos = new Vector3f();
		context.pushTransform(quad -> {
			for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
				quad.copyPos(vertexIndex, vertexPos);
				quad.spriteColor(vertexIndex, 0, color.getColor(vertexPos.getX() + pos.getX(), vertexPos.getY() + pos.getY(), vertexPos.getZ() + pos.getZ()));
			}
			return true;
		});
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}

}
