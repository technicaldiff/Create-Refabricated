package com.simibubi.create.lib.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface EntitySwingListenerItem {
	boolean onEntitySwing(ItemStack stack, LivingEntity entity);
}
