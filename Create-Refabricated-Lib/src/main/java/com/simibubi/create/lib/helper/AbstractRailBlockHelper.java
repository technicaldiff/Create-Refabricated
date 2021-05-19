package com.simibubi.create.lib.helper;

import javax.annotation.Nullable;

import com.simibubi.create.lib.extensions.AbstractRailBlockExtensions;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class AbstractRailBlockHelper {
	public static RailShape getDirectionOfRail(BlockState state, IBlockReader world, BlockPos pos, @Nullable AbstractMinecartEntity cart) {
		return ((AbstractRailBlockExtensions) cart).create$getRailDirection(state, world, pos, cart);
	}

	public static RailShape getDirectionOfRail(BlockState state, @Nullable AbstractMinecartEntity cart) {
		return ((AbstractRailBlockExtensions) cart).create$getRailDirection(state);
	}
}
