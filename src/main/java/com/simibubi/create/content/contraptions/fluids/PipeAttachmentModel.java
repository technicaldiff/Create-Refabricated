package com.simibubi.create.content.contraptions.fluids;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.fluids.FluidTransportBehaviour.AttachmentTypes;
import com.simibubi.create.content.contraptions.fluids.pipes.FluidPipeBlock;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedTileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.Iterate;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class PipeAttachmentModel extends ForwardingBakedModel {

	public PipeAttachmentModel(IBakedModel template) {
		wrapped = template;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(IBlockDisplayReader blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
		PipeModelData data = new PipeModelData();
		FluidTransportBehaviour transport = TileEntityBehaviour.get(blockView, pos, FluidTransportBehaviour.TYPE);
		BracketedTileEntityBehaviour bracket = TileEntityBehaviour.get(blockView, pos, BracketedTileEntityBehaviour.TYPE);

		if (transport != null)
			for (Direction d : Iterate.directions)
				data.putRim(d, transport.getRenderedRimAttachment(blockView, pos, state, d));
		if (bracket != null)
			data.putBracket(bracket.getBracket());

		data.setEncased(FluidPipeBlock.shouldDrawCasing(blockView, pos, state));

		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);

		for (Direction d : Iterate.directions)
			if (data.hasRim(d))
				context.fallbackConsumer().accept(AllBlockPartials.PIPE_ATTACHMENTS.get(data.getRim(d)).get(d).get());
		if (data.isEncased())
			context.fallbackConsumer().accept(AllBlockPartials.FLUID_PIPE_CASING.get());
		IBakedModel bracket1 = data.getBracket();
		if (bracket1 != null)
			context.fallbackConsumer().accept(bracket1);
	}

	private class PipeModelData {
		AttachmentTypes[] rims;
		boolean encased;
		IBakedModel bracket;

		public PipeModelData() {
			rims = new AttachmentTypes[6];
			Arrays.fill(rims, AttachmentTypes.NONE);
		}

		public void putBracket(BlockState state) {
			this.bracket = Minecraft.getInstance()
				.getBlockRendererDispatcher()
				.getModelForState(state);
		}

		public IBakedModel getBracket() {
			return bracket;
		}

		public void putRim(Direction face, AttachmentTypes rim) {
			rims[face.getIndex()] = rim;
		}

		public void setEncased(boolean encased) {
			this.encased = encased;
		}

		public boolean hasRim(Direction face) {
			return rims[face.getIndex()] != AttachmentTypes.NONE;
		}

		public AttachmentTypes getRim(Direction face) {
			return rims[face.getIndex()];
		}

		public boolean isEncased() {
			return encased;
		}
	}

}
