package com.simibubi.create.lib.utility;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class RailUtil {
	// doesn't support modded activator rails

	public static boolean isActivatorRail(Block rail) {
		return rail == Blocks.ACTIVATOR_RAIL;
	}
}
