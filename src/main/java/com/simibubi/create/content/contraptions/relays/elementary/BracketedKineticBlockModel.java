package com.simibubi.create.content.contraptions.relays.elementary;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;

import com.simibubi.create.foundation.block.entity.BlockEntityBehaviour;
import com.simibubi.create.foundation.block.render.HiddenBakedModel;
import com.simibubi.create.foundation.block.render.WrappedBakedModel;

public class BracketedKineticBlockModel extends WrappedBakedModel implements FabricBakedModel, HiddenBakedModel {
	private boolean hidden = true;

	public BracketedKineticBlockModel(BakedModel template) {
		super(template);
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
		if (!hidden) {
			context.fallbackConsumer().accept(this);
			return;
		}
		BracketedBlockEntityBehaviour attachmentBehaviour = BlockEntityBehaviour.get(blockView, pos, BracketedBlockEntityBehaviour.TYPE);
		if (attachmentBehaviour != null && attachmentBehaviour.isBracketPresent()) {
			BlockState bracketState = attachmentBehaviour.getBracket();
			BakedModel bracketModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(bracketState);
			context.fallbackConsumer().accept(bracketModel);
		}
	}

	@Override
	public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
		context.fallbackConsumer().accept(this);
	}

	@Override
	public boolean isHidden() {
		return hidden;
	}

	@Override
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
