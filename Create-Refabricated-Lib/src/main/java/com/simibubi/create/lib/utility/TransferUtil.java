package com.simibubi.create.lib.utility;

import org.jetbrains.annotations.Nullable;

import com.simibubi.create.lib.lba.item.IItemHandler;

import alexiil.mc.lib.attributes.SearchOption;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TransferUtil {
	@Nullable
	public static LazyOptional<IItemHandler> getItemHandler(World world, BlockPos pos, SearchOption option, boolean insert) {
		if (insert) {
			return LazyOptional.of(() -> (IItemHandler) ItemAttributes.INSERTABLE.getFirstOrNull(world, pos, option));
		} else {
			return LazyOptional.of(() -> (IItemHandler) ItemAttributes.EXTRACTABLE.getFirstOrNull(world, pos, option));
		}
	}
}
