package com.simibubi.create.lib.lba.item;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotItemHandler extends Slot {
	public SlotItemHandler(IInventory iInventory, int i, int j, int k) {
		super(iInventory, i, j, k);
	}

	public SlotItemHandler(IItemHandler handler, int index, int x, int y) {
		super(handlerToInv(handler), index, x, y);
	}

	public static Inventory handlerToInv(IItemHandler handler) {
		ItemStack[] itemStacks = new ItemStack[handler.getSlots()];
		for (int i = 0; i < handler.getSlots(); i++) {
			itemStacks[i] = handler.getStackInSlot(i);
		}
		return new Inventory(itemStacks);
	}

	/**
	 * Use second constructor instead
	 */
	@Deprecated
	public static SlotItemHandler create(IItemHandler handler, int index, int x, int y) {
		ItemStack[] itemStacks = new ItemStack[handler.getSlots()];
		for (int i = 0; i < handler.getSlots(); i++) {
			itemStacks[i] = handler.getStackInSlot(i);
		}
		Inventory inv = new Inventory(itemStacks);
		return new SlotItemHandler(inv, index, x, y);
	}
}
