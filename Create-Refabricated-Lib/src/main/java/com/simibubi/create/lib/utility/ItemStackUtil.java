package com.simibubi.create.lib.utility;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ItemStackUtil {
	public static boolean equals(ItemStack stack1, ItemStack stack2, boolean limitTags) {
		if (stack1.isEmpty()) {
			return stack2.isEmpty();
		} else {
			return !stack2.isEmpty() && stack1.getCount() == stack2.getCount() && stack1.getItem() == stack2.getItem() &&
					(limitTags ? areTagsEqual(stack1, stack2) : ItemStack.areItemStackTagsEqual(stack1, stack2));
		}
	}

	public static boolean areTagsEqual(ItemStack stack1, ItemStack stack2) {
		CompoundNBT tag1 = stack1.getTag();
		CompoundNBT tag2 = stack2.getTag();
		if (tag1 == null) {
			return tag2 == null;
		} else {
			return tag1.equals(tag2);
		}
	}
}
