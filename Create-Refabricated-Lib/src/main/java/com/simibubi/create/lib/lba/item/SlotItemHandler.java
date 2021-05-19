package com.simibubi.create.lib.lba.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotItemHandler extends Slot {
	protected SlotItemHandler(IInventory iInventory, int i, int j, int k) {
		super(iInventory, i, j, k);
	}

	public static SlotItemHandler create(IItemHandler handler, int index, int x, int y) {
		List<ItemStack> itemStacks = new ArrayList<>();
		for (int i = 1; i <= handler.getSlots(); i++) {
			itemStacks.add(handler.getStackInSlot(i));
		}
		Inventory inv = new Inventory((ItemStack[]) itemStacks.toArray());
		return new SlotItemHandler(inv, index, x, y);
	}
}
