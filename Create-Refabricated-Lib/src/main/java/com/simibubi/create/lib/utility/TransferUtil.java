package com.simibubi.create.lib.utility;

import com.simibubi.create.lib.lba.fluid.IFluidHandler;
import com.simibubi.create.lib.lba.item.IItemHandler;

import alexiil.mc.lib.attributes.SearchOption;
import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.fluid.FluidAttributes;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TransferUtil {
	// items

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

	// fluids

	public static LazyOptional<IFluidHandler> getFluidHandler(World world, BlockPos pos, SearchOption option, boolean insert) {
		if (insert) {
			return LazyOptional.of(() -> (IFluidHandler) FluidAttributes.INSERTABLE.getFirstOrNull(world, pos, option));
		} else {
			return LazyOptional.of(() -> (IFluidHandler) FluidAttributes.EXTRACTABLE.getFirstOrNull(world, pos, option));
		}
	}

	public static LazyOptional<IFluidHandler> getFluidHandler(World world, BlockPos pos, SearchOption option) {
		return getFluidHandler(world, pos, option, true);
	}

	public static LazyOptional<IFluidHandler> getFluidHandler(TileEntity entity) {
		return getFluidHandler(entity.getWorld(), entity.getPos(), SearchOptions.ALL, true);
	}

	public static LazyOptional<IFluidHandler> getFluidHandler(World world, BlockPos pos, Direction side, boolean insert) {
		return getFluidHandler(world, pos, SearchOptions.inDirection(side), insert);
	}

	public static LazyOptional<IFluidHandler> getFluidHandler(World world, BlockPos pos, Direction side) {
		return getFluidHandler(world, pos, SearchOptions.inDirection(side), true);
	}
}
