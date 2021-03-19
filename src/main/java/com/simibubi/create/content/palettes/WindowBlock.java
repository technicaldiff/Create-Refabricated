package com.simibubi.create.content.palettes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.util.math.Direction;

public class WindowBlock extends ConnectedGlassBlock {
	public WindowBlock(Settings p_i48392_1_) {
		super(p_i48392_1_);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() instanceof ConnectedGlassBlock
			? (RenderLayers.getBlockLayer(state) != RenderLayer.getTranslucent() && side.getAxis()
				.isHorizontal() || state.getBlock() == adjacentBlockState.getBlock())
			: super.isSideInvisible(state, adjacentBlockState, side);
	}
}
