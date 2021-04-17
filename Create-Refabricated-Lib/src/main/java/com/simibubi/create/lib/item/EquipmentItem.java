package com.simibubi.create.lib.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public interface EquipmentItem {
	EquipmentSlotType getEquipmentSlot(ItemStack stack);
}
