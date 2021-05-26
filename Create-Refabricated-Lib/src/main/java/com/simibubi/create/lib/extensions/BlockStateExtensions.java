package com.simibubi.create.lib.extensions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public interface BlockStateExtensions {

	default boolean create$addRunningEffects(World world, BlockPos pos, Entity entity) {
		return ((BlockExtensions) ((BlockState) this).getBlock()).create$addRunningEffects((BlockState) this, world, pos, entity);
	}

	default boolean create$addLandingEffects(ServerWorld worldserver, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
		return ((BlockExtensions) ((BlockState) this).getBlock()).create$addLandingEffects((BlockState) this, worldserver, pos, state2, entity, numberOfParticles);
	}

	@Environment(EnvType.CLIENT)
	default boolean create$addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		return ((BlockExtensions) ((BlockState) this).getBlock()).create$addDestroyEffects((BlockState) this, world, pos, manager);
	}

	default boolean create$isFlammable(IBlockReader world, BlockPos pos, Direction face) {
		return ((BlockExtensions) ((BlockState) this).getBlock()).create$isFlammable((BlockState) this, world, pos, face);
	}

	default int create$getFlammability(IBlockReader world, BlockPos pos, Direction face) {
		return ((BlockExtensions) ((BlockState) this).getBlock()).create$getFlammability((BlockState) this, world, pos, face);
	}

	default void create$onNeighborChange(IWorldReader world, BlockPos pos, BlockPos neighbor) {
		((BlockExtensions) ((BlockState) this).getBlock()).create$onNeighborChange((BlockState) this, world, pos, neighbor);
	}
}
