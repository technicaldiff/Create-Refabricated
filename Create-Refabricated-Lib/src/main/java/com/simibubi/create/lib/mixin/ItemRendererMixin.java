package com.simibubi.create.lib.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.lib.item.CustomGuiOverlayItem;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	// TODO: improve this to not use a redirect
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamaged()Z"), method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V")
	public boolean checkRenderDurabilityBar(ItemStack stack, TextRenderer textRenderer, ItemStack stack1, int x, int y) {
		Item item = stack.getItem();
		if (item instanceof CustomGuiOverlayItem && ((CustomGuiOverlayItem) item).renderOverlay(stack, x, y, textRenderer, (ItemRenderer) (Object) this)) {
			return false;
		}
		return stack.isDamaged();
	}
}
