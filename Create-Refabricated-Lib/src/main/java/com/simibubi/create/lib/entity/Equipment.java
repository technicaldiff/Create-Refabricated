package com.simibubi.create.lib.entity;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public interface Equipment {
	public EquipmentSlotType getEquipmentSlot(ItemStack stack);
}
