package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.FirstPersonRendererAccessor;

import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.item.ItemStack;

public class FirstPersonRendererHelper {
	public static ItemStack getStackInMainHand(FirstPersonRenderer renderer) {
		return ((FirstPersonRendererAccessor) renderer).getItemStackMainHand();
	}

	public static ItemStack getStackInOffHand(FirstPersonRenderer renderer) {
		return ((FirstPersonRendererAccessor) renderer).getItemStackOffHand();
	}
}
