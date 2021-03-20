package com.simibubi.create.foundation.mixin;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.curiosities.ChromaticCompoundItem;
import com.simibubi.create.content.curiosities.RefinedRadianceItem;
import com.simibubi.create.content.curiosities.ShadowSteelItem;
import com.simibubi.create.foundation.utility.MixinHelper;
import net.minecraft.entity.Entity;
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
		//boolean test = ChromaticCompoundItem.onEntityItemUpdate(MixinHelper.<ItemEntity>cast(this).getStack(), MixinHelper.<ItemEntity>cast(this));
		//test = RefinedRadianceItem.onEntityItemUpdate(MixinHelper.<ItemEntity>cast(this).getStack(), MixinHelper.<ItemEntity>cast(this));
		//test = ShadowSteelItem.onEntityItemUpdate(MixinHelper.<ItemEntity>cast(this).getStack(), MixinHelper.<ItemEntity>cast(this));
		//MixinHelper.<Entity>cast(this).setNoGravity(false);
	}
}
