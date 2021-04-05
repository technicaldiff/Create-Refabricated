package com.simibubi.create.content.palettes;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ConnectedGlassBlock extends GlassBlock {

	public ConnectedGlassBlock(Properties p_i48392_1_) {
		super(p_i48392_1_);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() instanceof ConnectedGlassBlock ? true
			: super.isSideInvisible(state, adjacentBlockState, side);
	}

	@Override
	public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
		return true;
	}
}
