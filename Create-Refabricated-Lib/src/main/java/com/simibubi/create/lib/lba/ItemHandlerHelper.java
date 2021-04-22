package com.simibubi.create.lib.lba;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

public final class ItemHandlerHelper { // name is this to maintain max compat with upstream
	public static boolean canItemStacksStack(@Nonnull ItemStack a, @Nonnull ItemStack b) {
		if (a.isEmpty() || !a.isItemEqual(b) || a.hasTag() != b.hasTag()) return false;
		return !a.hasTag() || a.getTag().equals(b.getTag());
	}

	@Nonnull
	public static ItemStack copyStackWithSize(@Nonnull ItemStack itemStack, int size) {
		if (size == 0) return ItemStack.EMPTY;
		ItemStack copy = itemStack.copy();
		copy.setCount(size);
		return copy;
	}
}
