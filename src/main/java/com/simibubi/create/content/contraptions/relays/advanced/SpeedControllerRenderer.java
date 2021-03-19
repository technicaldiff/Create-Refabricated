package com.simibubi.create.content.contraptions.relays.advanced;

import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.block.entity.render.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.render.backend.FastRenderDispatcher;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.World;

public class SpeedControllerRenderer extends SmartBlockEntityRenderer<SpeedControllerBlockEntity> {

	public SpeedControllerRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected void renderSafe(SpeedControllerBlockEntity tileEntityIn, float partialTicks, MatrixStack ms,
							  VertexConsumerProvider buffer, int light, int overlay) {
		super.renderSafe(tileEntityIn, partialTicks, ms, buffer, light, overlay);

		VertexConsumer builder = buffer.getBuffer(RenderLayer.getSolid());
		if (!FastRenderDispatcher.available(tileEntityIn.getWorld())) {
			KineticBlockEntityRenderer.renderRotatingBuffer(tileEntityIn, getRotatedModel(tileEntityIn), ms, builder, light);
		}

		if (!tileEntityIn.hasBracket)
			return;

		BlockPos pos = tileEntityIn.getPos();
		World world = tileEntityIn.getWorld();
		BlockState blockState = tileEntityIn.getCachedState();

		SuperByteBuffer bracket = AllBlockPartials.SPEED_CONTROLLER_BRACKET.renderOn(blockState);
		bracket.translate(0, 1, 0);
		bracket.rotateCentered(Direction.UP,
			(float) (blockState.get(SpeedControllerBlock.HORIZONTAL_AXIS) == Axis.X ? Math.PI : 0));
		bracket.light(WorldRenderer.getLightmapCoordinates(world, pos.up()));
		bracket.renderInto(ms, builder);
	}

	private SuperByteBuffer getRotatedModel(SpeedControllerBlockEntity te) {
		return CreateClient.bufferCache.renderBlockIn(KineticBlockEntityRenderer.KINETIC_TILE,
			KineticBlockEntityRenderer.shaft(KineticBlockEntityRenderer.getRotationAxisOf(te)));
	}

}
