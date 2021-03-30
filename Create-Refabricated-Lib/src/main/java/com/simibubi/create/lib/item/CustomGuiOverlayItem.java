package com.simibubi.create.lib.item;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;

public interface CustomGuiOverlayItem {
	/**
	 * Returning true cancels the default durability bar rendering.
	 */
	boolean renderOverlay(ItemStack stack, int x, int y, TextRenderer textRenderer, ItemRenderer itemRenderer);
}
