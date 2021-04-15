package com.simibubi.create.lib.entity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface CustomPathfindingBehavior {
	PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity);
}
