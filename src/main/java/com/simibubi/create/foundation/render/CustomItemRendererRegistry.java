package com.simibubi.create.foundation.render;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

public final class CustomItemRendererRegistry {
	private static final Map<Item, CustomItemRenderer> RENDERERS = new HashMap<>();

	public static void registerRenderer(Item item, CustomItemRenderer renderer) {
		RENDERERS.put(item, renderer);
	}

	public static CustomItemRenderer getRenderer(Item item) {
		return RENDERERS.get(item);
	}

	private CustomItemRendererRegistry() { }
}
