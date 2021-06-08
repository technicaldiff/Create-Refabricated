package com.simibubi.create.lib.utility;

import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.ItemGroup;

public class ItemGroupUtil {
	public static synchronized int getGroupCountSafe() {
		((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
		return ItemGroup.GROUPS.length - 1;
	}
}
