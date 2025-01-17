package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(FirstPersonRenderer.class)
public interface FirstPersonRendererAccessor {
	@Accessor("itemStackMainHand")
	ItemStack getItemStackMainHand();

	@Accessor("itemStackOffHand")
	ItemStack getItemStackOffHand();
}
