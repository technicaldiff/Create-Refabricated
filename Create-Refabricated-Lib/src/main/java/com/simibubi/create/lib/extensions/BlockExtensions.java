package com.simibubi.create.lib.extensions;

import javax.annotation.Nullable;

import com.simibubi.create.lib.mixin.accessor.FireBlockAccessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public interface BlockExtensions {
	default boolean create$addRunningEffects(BlockState state, World world, BlockPos pos, Entity entity) {
		return false;
	}

	default boolean create$addLandingEffects(BlockState state1, ServerWorld worldserver, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
		return false;
	}

	@Environment(EnvType.CLIENT)
	default boolean create$addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
		return false;
	}

	default boolean create$isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return ((BlockStateExtensions) state).create$getFlammability(world, pos, face) > 0;
	}

	default int create$getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return ((FireBlockAccessor) ((FireBlock) Blocks.FIRE)).func_220274_q(state);
	}

	default SoundType create$getSoundType(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
		return ((Block) this).getSoundType(state);
	}

	default int create$getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getLightValue();
	}

	default boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
		return state.getBlock() instanceof BreakableBlock || state.getBlock() instanceof LeavesBlock;
	}
}
