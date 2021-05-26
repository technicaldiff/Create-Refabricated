package com.simibubi.create.content.contraptions.relays.elementary;

import java.util.Random;
import java.util.function.Supplier;

import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.lib.render.VirtualRenderingStateManager;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class BracketedKineticBlockModel extends ForwardingBakedModel {

	public BracketedKineticBlockModel(IBakedModel template) {
		wrapped = template;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(IBlockDisplayReader blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
		if (VirtualRenderingStateManager.getVirtualState()) {
			super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
			return;
		}

		BracketedModelData data = new BracketedModelData();
		BracketedTileEntityBehaviour attachmentBehaviour =
			TileEntityBehaviour.get(blockView, pos, BracketedTileEntityBehaviour.TYPE);
		if (attachmentBehaviour != null)
			data.putBracket(attachmentBehaviour.getBracket());

		IBakedModel bracket = data.getBracket();
		if (bracket == null)
			return;
		context.fallbackConsumer().accept(bracket);
	}

	private class BracketedModelData {
		IBakedModel bracket;

		public void putBracket(BlockState state) {
			this.bracket = Minecraft.getInstance()
				.getBlockRendererDispatcher()
				.getModelForState(state);
		}

		public IBakedModel getBracket() {
			return bracket;
		}

	}

}
