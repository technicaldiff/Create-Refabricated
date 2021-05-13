package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.item.ItemStack;

@Mixin(FirstPersonRenderer.class)
public interface FirstPersonRendererAccessor {
	@Accessor("itemStackMainHand")
	public ItemStack getItemStackMainHand();

	@Accessor("itemStackOffHand")
	public ItemStack getItemStackOffHand();
}
