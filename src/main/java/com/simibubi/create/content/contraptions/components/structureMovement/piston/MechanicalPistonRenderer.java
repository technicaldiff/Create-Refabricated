// PORTED CREATE SOURCE

package com.simibubi.create.content.contraptions.components.structureMovement.piston;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

import com.simibubi.create.content.contraptions.base.KineticBlockEntity;
import com.simibubi.create.content.contraptions.base.KineticBlockEntityRenderer;

public class MechanicalPistonRenderer extends KineticBlockEntityRenderer {

	public MechanicalPistonRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected BlockState getRenderedBlockState(KineticBlockEntity te) {
		return shaft(getRotationAxisOf(te));
	}

}
