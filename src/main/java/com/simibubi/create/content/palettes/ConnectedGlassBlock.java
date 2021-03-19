package com.simibubi.create.content.palettes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.util.math.Direction;

public class ConnectedGlassBlock extends GlassBlock {
	public ConnectedGlassBlock(Settings p_i48392_1_) {
		super(p_i48392_1_);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() instanceof ConnectedGlassBlock ? true
			: super.isSideInvisible(state, adjacentBlockState, side);
	}

	// Should be fine because this class is a subclass of TransparentBlock
//	@Override
//	public boolean shouldDisplayFluidOverlay(BlockState state, BlockRenderView world, BlockPos pos, FluidState fluidState) {
//		return true;
//	}
}
