package com.simibubi.create.lib.item;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;

public interface EntityTickListenerItem {
	boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity);
}
