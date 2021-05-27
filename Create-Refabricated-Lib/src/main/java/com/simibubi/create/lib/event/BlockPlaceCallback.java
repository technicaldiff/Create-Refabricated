package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public interface BlockPlaceCallback {
	public static final Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class, callbacks -> (context) -> {
		for (BlockPlaceCallback callback : callbacks) {
			ActionResultType result = callback.onBlockPlace(context);
			if (result != ActionResultType.PASS) {
				return result;
			}
		}
		return ActionResultType.PASS;
	});

	ActionResultType onBlockPlace(ItemUseContext context);
}
