package com.simibubi.create.lib.lba.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class RecipeWrapper implements IInventory {
	public IItemHandlerModifiable inv;

	public RecipeWrapper(IItemHandlerModifiable inv) {
		this.inv = inv;
	}

	@Override
	public int getSizeInventory() {
		return inv.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		ItemStack stack = inv.getStackInSlot(slot);
		return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv.setStackInSlot(slot, stack);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		if(stack.isEmpty()) return ItemStack.EMPTY;
		setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public boolean isEmpty() {
		for(int i = 0; i < inv.getSlots(); i++) {
			if(!inv.getStackInSlot(i).isEmpty()) return false;
		}
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return inv.isItemValid(slot, stack);
	}

	@Override
	public void clear() {
		for(int i = 0; i < inv.getSlots(); i++) {
			inv.setStackInSlot(i, ItemStack.EMPTY);
		}
	}

	@Override
	public void markDirty() {}
	@Override
	public boolean isUsableByPlayer(PlayerEntity playerEntity) {return false;}
}
