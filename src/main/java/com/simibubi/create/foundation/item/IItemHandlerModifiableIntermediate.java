package com.simibubi.create.foundation.item;

import com.simibubi.create.lib.lba.IItemHandlerModifiable;

import net.minecraft.item.ItemStack;

interface IItemHandlerModifiableIntermediate extends IItemHandlerModifiable {

	@Override
	public default ItemStack getStackInSlot(int slot) {
		return getStackInSlotIntermediate(slot);
	}

	public ItemStack getStackInSlotIntermediate(int slot);

}
