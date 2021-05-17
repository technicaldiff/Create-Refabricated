package com.simibubi.create.lib.extensions;

import java.util.function.Supplier;

import net.minecraft.item.Item;

public interface ItemExtensions {
	Supplier<Item> getSupplier();
}
