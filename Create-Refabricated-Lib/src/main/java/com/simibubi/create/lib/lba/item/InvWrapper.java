package com.simibubi.create.lib.lba.item;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InvWrapper implements IItemHandlerModifiable{
	public IInventory inv;
	public InvWrapper(IInventory inv) {
		this.inv = inv;
	}

	@Override
	public int getSlots() {
		return inv.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (inv.isItemValidForSlot(slot, stack)) {
			inv.setInventorySlotContents(slot, stack);
			return ItemStack.EMPTY;
		} else {
			ItemStack stackInSlot = inv.getStackInSlot(slot).copy();
			if (stackInSlot.getItem() == stack.getItem()) {
				// transferring items
				for (int i = stackInSlot.getCount(); i <= stackInSlot.getMaxStackSize() && i <= inv.getInventoryStackLimit(); i++) {
					stackInSlot.setCount(stackInSlot.getCount() + 1);
					stack.setCount(stack.getCount() - 1);
				}
				if (!simulate) {
					inv.setInventorySlotContents(slot, stackInSlot);
				}
				return stack;
			}
			return stack;
		}
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		inv.getStackInSlot(slot);
		ItemStack stackInSlot = inv.getStackInSlot(slot).copy();
		ItemStack returnStack = new ItemStack(stackInSlot.getItem());
		// transferring items
		for (int i = 0; i < amount; i++) {
			stackInSlot.setCount(stackInSlot.getCount() - 1);
			returnStack.setCount(returnStack.getCount() + 1);
		}
		if (!simulate) {
			inv.setInventorySlotContents(slot, stackInSlot);
		}
		return returnStack;
	}

	@Override
	public int getSlotLimit(int slot) {
		return inv.getInventoryStackLimit();
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return inv.isItemValidForSlot(slot, stack);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		inv.setInventorySlotContents(slot, stack);
	}
}
