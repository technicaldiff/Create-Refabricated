package com.simibubi.create.lib.helper;

import net.minecraft.item.Item;

public class CustomDurabilityPropertyHelper extends Item.Properties {
	public static Item.Properties setMaxDamage(int damage, Item.Properties properties) {
		Item.Properties newProperty = properties;
		newProperty.maxDamage(damage);
		return newProperty;
	}
}
