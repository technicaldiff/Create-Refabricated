package com.simibubi.create.lib.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.item.EntityTickListenerItem;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/entity/ItemEntity;tick()V", cancellable = true)
	public void create$onHeadTick(CallbackInfo ci) {
		ItemEntity self = (ItemEntity) (Object) this;
		ItemStack stack = self.getStack();
		if (stack.getItem() instanceof EntityTickListenerItem && ((EntityTickListenerItem) stack.getItem()).onEntityItemUpdate(stack, self)) {
			ci.cancel();
		}
	}
}
