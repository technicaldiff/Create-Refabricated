package com.simibubi.create.lib.helper;

import java.util.function.Supplier;

import net.minecraft.item.Item;

public class ItemSupplier implements Supplier<Item> {
	private Item item;

	@Override
	public Item get() {
		return item;
	}

	public ItemSupplier(Item item) {
		this.item = item;
	}
}
