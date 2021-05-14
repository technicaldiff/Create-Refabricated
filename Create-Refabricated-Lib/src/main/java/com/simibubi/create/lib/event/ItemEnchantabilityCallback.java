package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

public interface ItemEnchantabilityCallback {
	public static final Event<ItemEnchantabilityCallback> EVENT = EventFactory.createArrayBacked(ItemEnchantabilityCallback.class, callbacks -> (stack) -> {
		for (ItemEnchantabilityCallback callback : callbacks) {
			return callback.onItemEnchantability(stack);
		}

		return 0;
	});

	int onItemEnchantability(ItemStack stack);
}
