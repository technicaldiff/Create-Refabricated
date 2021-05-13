package com.simibubi.create.lib.utility;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LoadedCheckUtil {
	public static boolean isAreaLoaded(World world, BlockPos center, int range) {
		return world.isAreaLoaded(center.add(-range, -range, -range), center.add(range, range, range));
	}
}
