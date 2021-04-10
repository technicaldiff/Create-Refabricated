package com.simibubi.create.lib.item;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public interface CustomGuiOverlayItem {
	/**
	 * Returning true cancels the default durability bar rendering.
	 */
	boolean renderOverlay(ItemStack stack, int x, int y, FontRenderer textRenderer, ItemRenderer itemRenderer);
}
