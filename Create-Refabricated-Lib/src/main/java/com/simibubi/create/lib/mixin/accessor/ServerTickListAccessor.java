package com.simibubi.create.lib.mixin.accessor;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.server.ServerTickList;

@Mixin(ServerTickList.class)
public interface ServerTickListAccessor<T> {
	@Accessor("pendingTickListEntriesHashSet")
	Set<NextTickListEntry<T>> getPendingTickListEntriesHashSet();
}
