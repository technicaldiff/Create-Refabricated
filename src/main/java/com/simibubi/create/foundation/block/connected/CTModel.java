// PORTED CREATE SOURCE

package com.simibubi.create.foundation.block.connected;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;

import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour.CTContext;
import com.simibubi.create.foundation.utility.Iterate;

public class CTModel extends ForwardingBakedModel {
	private static final ThreadLocal<SpriteFinder> FINDER = ThreadLocal.withInitial(() -> SpriteFinder.get(MinecraftClient.getInstance().getBakedModelManager().method_24153(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)));

	private ConnectedTextureBehaviour behaviour;

	private class CTData {
		int[] indices;

		public CTData() {
			indices = new int[6];
			Arrays.fill(indices, -1);
		}

		void put(Direction face, int texture) {
			indices[face.getId()] = texture;
		}

		int get(Direction face) {
			return indices[face.getId()];
		}
	}

	public CTModel(BakedModel originalModel, ConnectedTextureBehaviour behaviour) {
		wrapped = originalModel;
		this.behaviour = behaviour;
	}

	protected CTData createCTData(BlockRenderView world, BlockPos pos, BlockState state) {
		CTData data = new CTData();
		for (Direction face : Iterate.directions) {
			if (!Block.shouldDrawSide(state, world, pos, face) && !behaviour.buildContextForOccludedDirections())
				continue;
			CTSpriteShiftEntry spriteShift = behaviour.get(state, face);
			if (spriteShift == null)
				continue;
			CTContext ctContext = behaviour.buildContext(world, pos, state, face);
			data.put(face, spriteShift.getTextureIndex(ctContext));
		}
		return data;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
		CTData data = createCTData(blockView, pos, state);
		SpriteFinder finder = FINDER.get();
		context.pushTransform(quad -> {
			CTSpriteShiftEntry spriteShift = behaviour.get(state, quad.lightFace());
			if (spriteShift == null)
				return true;
			if (finder.find(quad, 0) != spriteShift.getOriginal())
				return true;
			int index = data.get(quad.lightFace());
			if (index == -1)
				return true;

			for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
				float u = quad.spriteU(vertexIndex, 0);
				float v = quad.spriteV(vertexIndex, 0);
				u = spriteShift.getTargetU(u, index);
				v = spriteShift.getTargetV(v, index);
				quad.sprite(vertexIndex, 0, u, v);
			}

			return true;
		});
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}
}
