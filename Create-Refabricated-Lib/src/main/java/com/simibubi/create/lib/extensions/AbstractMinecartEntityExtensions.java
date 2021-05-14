package com.simibubi.create.lib.extensions;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public interface AbstractMinecartEntityExtensions {
	void create$moveMinecartOnRail(BlockPos pos);

	ItemStack create$getCartItem();

	boolean create$canUseRail();

	BlockPos getCurrentRailPos();

	default float getMaxSpeedOnRail() {
		return 1.2f; // default in Forge
	}
}
