package com.simibubi.create.lib.helper;

import java.util.Set;

import com.simibubi.create.lib.mixin.accessor.ServerTickListAccessor;

import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.server.ServerTickList;

public class ServerTickListHelper {
	public static Set<NextTickListEntry> getPendingTickListEntries(ServerTickList list) {
		return ((ServerTickListAccessor) list).getPendingTickListEntriesHashSet();
	}
}
