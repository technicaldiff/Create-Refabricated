package com.simibubi.create.lib.utility;

import net.minecraft.item.ItemGroup;

public class ItemGroupUtil {
	public static synchronized int getGroupCountSafe() {
		return ItemGroup.GROUPS.length - 1;
	}
}
