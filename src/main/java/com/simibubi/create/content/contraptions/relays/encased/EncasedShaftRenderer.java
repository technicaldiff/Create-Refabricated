package com.simibubi.create.content.contraptions.relays.encased;

import com.simibubi.create.content.contraptions.base.KineticBlockEntity;
import com.simibubi.create.content.contraptions.base.KineticBlockEntityRenderer;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

public class EncasedShaftRenderer extends KineticBlockEntityRenderer {

	public EncasedShaftRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected BlockState getRenderedBlockState(KineticBlockEntity te) {
		return shaft(getRotationAxisOf(te));
	}

}
