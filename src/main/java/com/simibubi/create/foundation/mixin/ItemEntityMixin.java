package com.simibubi.create.foundation.mixin;

import com.simibubi.create.content.curiosities.ChromaticCompoundItem;

import com.simibubi.create.content.curiosities.RefinedRadianceItem;
import com.simibubi.create.content.curiosities.ShadowSteelItem;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.entity.ItemEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Deprecated // This should be replaced.
@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	@Inject(method = "Lnet/minecraft/entity/ItemEntity;tick()V", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		if (ChromaticCompoundItem.onEntityItemUpdate(MixinHelper.<ItemEntity>cast(this).getStack(), MixinHelper.<ItemEntity>cast(this))) return;
		if (RefinedRadianceItem.onEntityItemUpdate(MixinHelper.<ItemEntity>cast(this).getStack(), MixinHelper.<ItemEntity>cast(this))) return;
		if (ShadowSteelItem.onEntityItemUpdate(MixinHelper.<ItemEntity>cast(this).getStack(), MixinHelper.<ItemEntity>cast(this))) return;
		//MixinHelper.<Entity>cast(this).setNoGravity(false);
	}
}
