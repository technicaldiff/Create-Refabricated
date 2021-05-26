package com.simibubi.create.content.contraptions.relays.belt;

import java.util.Random;
import java.util.function.Supplier;

import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.contraptions.relays.belt.BeltTileEntity.CasingType;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class BeltModel extends ForwardingBakedModel {

	private static final ThreadLocal<SpriteFinder> SPRITE_FINDER = ThreadLocal.withInitial(() -> SpriteFinder.get(Minecraft.getInstance().getModelManager().method_24153(AtlasTexture.LOCATION_BLOCKS_TEXTURE)));

	public BeltModel(IBakedModel template) {
		wrapped = template;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(IBlockDisplayReader blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
		Object attachment = ((RenderAttachedBlockView) blockView).getBlockEntityRenderAttachment(pos);
		boolean applyTransform = false;
		if (attachment instanceof CasingType) {
			CasingType type = (CasingType) attachment;
			applyTransform = !(type == CasingType.NONE || type == CasingType.BRASS);
		}
		if (applyTransform) {
			SpriteShiftEntry spriteShift = AllSpriteShifts.ANDESIDE_BELT_CASING;
			TextureAtlasSprite target = spriteShift.getTarget();
			context.pushTransform(quad -> {
				TextureAtlasSprite original = SPRITE_FINDER.get().find(quad, 0);
				for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
					float u = quad.spriteU(vertexIndex, 0);
					float v = quad.spriteV(vertexIndex, 0);
					u = target.getInterpolatedU(SuperByteBuffer.getUnInterpolatedU(original, u));
					v = target.getInterpolatedV(SuperByteBuffer.getUnInterpolatedV(original, v));
					quad.sprite(vertexIndex, 0, u, v);
				}
				return true;
			});
		}
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		if (applyTransform) {
			context.popTransform();
		}
	}

}
