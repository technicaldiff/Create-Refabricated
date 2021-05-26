package com.simibubi.create.lib.utility;

import com.simibubi.create.lib.lba.item.IItemHandler;

import alexiil.mc.lib.attributes.SearchOption;
import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TransferUtil {
	public static LazyOptional<IItemHandler> getItemHandler(World world, BlockPos pos, SearchOption option, boolean insert) {
		if (insert) {
			return LazyOptional.of(() -> (IItemHandler) ItemAttributes.INSERTABLE.getFirstOrNull(world, pos, option));
		} else {
			return LazyOptional.of(() -> (IItemHandler) ItemAttributes.EXTRACTABLE.getFirstOrNull(world, pos, option));
		}
	}

	public static LazyOptional<IItemHandler> getItemHandler(World world, BlockPos pos, SearchOption option) {
		return getItemHandler(world, pos, option, true);
	}

	public static LazyOptional<IItemHandler> getItemHandler(World world, BlockPos pos, Direction side, boolean insert) {
		return getItemHandler(world, pos, SearchOptions.inDirection(side), insert);
	}

	public static LazyOptional<IItemHandler> getItemHandler(World world, BlockPos pos, Direction side) {
		return getItemHandler(world, pos, SearchOptions.inDirection(side), true);
	}
}
