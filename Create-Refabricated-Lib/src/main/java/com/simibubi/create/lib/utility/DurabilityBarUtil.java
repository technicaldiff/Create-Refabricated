package com.simibubi.create.lib.utility;

import com.simibubi.create.lib.item.CustomDurabilityBarItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public final class DurabilityBarUtil {
	public static boolean showDurabilityBarDefault(ItemStack stack) {
		return stack.isDamaged();
	}

	public static double getDurabilityForDisplayDefault(ItemStack stack) {
		return stack.getDamage() / stack.getMaxDamage();
	}

	public static int getRGBDurabilityForDisplayDefault(ItemStack stack) {
		return MathHelper.hsvToRGB((float) Math.max(0.0D, 1.0D - getDurabilityForDisplayDefault(stack)) / 3.0F, 1.0F, 1.0F);
	}

	public static boolean showDurabilityBar(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof CustomDurabilityBarItem) {
			return ((CustomDurabilityBarItem) item).showDurabilityBar(stack);
		}
		return showDurabilityBarDefault(stack);
	}

	public static double getDurabilityForDisplay(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof CustomDurabilityBarItem) {
			return ((CustomDurabilityBarItem) item).getDurabilityForDisplay(stack);
		}
		return getDurabilityForDisplayDefault(stack);
	}

	public static int getRGBDurabilityForDisplay(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof CustomDurabilityBarItem) {
			return ((CustomDurabilityBarItem) item).getRGBDurabilityForDisplay(stack);
		}
		return getRGBDurabilityForDisplayDefault(stack);
	}

	private DurabilityBarUtil() {}
}
