package com.simibubi.create.foundation.block.render;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

public class WrappedBakedModel implements IBakedModel {

	protected IBakedModel template;

	public WrappedBakedModel(IBakedModel template) {
		this.template = template;
	}
	
	public IBakedModel getBakedModel() {
		return template;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return template.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return template.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return template.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return template.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return template.getOverrides();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return template.getItemCameraTransforms();
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
		return template.getQuads(state, side, rand);
	}

	@Override
	public boolean isSideLit() {
		return template.isSideLit();
	}
}
