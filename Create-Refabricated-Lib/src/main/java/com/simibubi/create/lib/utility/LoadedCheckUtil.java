package com.simibubi.create.lib.utility;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class LoadedCheckUtil {
	public static boolean isAreaLoaded(IWorld world, BlockPos center, int range) {
		return world.isAreaLoaded(center.add(-range, -range, -range), center.add(range, range, range));
	}
}
