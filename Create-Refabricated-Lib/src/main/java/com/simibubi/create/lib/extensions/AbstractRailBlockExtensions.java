package com.simibubi.create.lib.extensions;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface AbstractRailBlockExtensions {
	RailShape create$getRailDirection(BlockState state, IBlockReader world, BlockPos pos, @Nullable AbstractMinecartEntity cart);

	RailShape create$getRailDirection(BlockState state);
}
