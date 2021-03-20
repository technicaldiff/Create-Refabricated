package com.simibubi.create.foundation.mixin;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.curiosities.ChromaticCompoundItem;
import com.simibubi.create.content.curiosities.RefinedRadianceItem;
import com.simibubi.create.content.curiosities.ShadowSteelItem;
import com.simibubi.create.foundation.utility.MixinHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	@Inject(method = "Lnet/minecraft/entity/ItemEntity;tick()V", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		boolean test = ChromaticCompoundItem.onEntityItemUpdate(new ItemStack(AllItems.CHROMATIC_COMPOUND), MixinHelper.cast(this));
		test = RefinedRadianceItem.onEntityItemUpdate(new ItemStack(AllItems.REFINED_RADIANCE), MixinHelper.cast(this));
		test = ShadowSteelItem.onEntityItemUpdate(new ItemStack(AllItems.SHADOW_STEEL), MixinHelper.cast(this));
	}
}
