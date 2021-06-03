package com.simibubi.create.lib.helper;

import java.util.Set;

import com.simibubi.create.lib.mixin.accessor.ServerTickListAccessor;

import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.server.ServerTickList;

public class ServerTickListHelper {
	public static <T> Set<NextTickListEntry<T>> getPendingTickListEntries(ServerTickList<T> list) {
		return ((ServerTickListAccessor<T>) list).getPendingTickListEntriesHashSet();
	}
}
