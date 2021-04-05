package com.simibubi.create.lib.item;

import net.minecraft.item.ItemStack;

public interface CustomMaxCountItem {
	int getItemStackLimit(ItemStack stack);
}
