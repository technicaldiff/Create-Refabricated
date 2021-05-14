package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.event.LivingEntityItemSwingCallback;
import com.simibubi.create.lib.event.LivingEntityExperienceDropCallback;
import com.simibubi.create.lib.event.LivingEntityKnockbackStrengthCallback;
import com.simibubi.create.lib.event.LivingEntityTickCallback;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	protected PlayerEntity attackingPlayer;

	@Shadow public abstract ItemStack getHeldItem(Hand hand);

	@Environment(EnvType.CLIENT)
	@Inject(method = "swingHand(Lnet/minecraft/util/Hand;Z)V", at = @At("HEAD"), cancellable = true)
	private void create$swingHand(Hand hand, boolean bl, CallbackInfo ci) {
		ItemStack stack = getHeldItem(hand);
		if (!stack.isEmpty() && LivingEntityItemSwingCallback.EVENT.invoker().onEntityItemSwing(stack, (LivingEntity) (Object) this)) ci.cancel();
	}

	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tick()V"))
	private void create$tick(CallbackInfo ci) {
		LivingEntityTickCallback.EVENT.invoker().onLivingEntityTick((LivingEntity) (Object) this);
	}

	@ModifyVariable(method = "takeKnockback(FDD)V", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
	private float create$takeKnockback(float f) {
		return LivingEntityKnockbackStrengthCallback.EVENT.invoker().onLivingEntityTakeKnockback(f, attackingPlayer);
	}

	@ModifyVariable(method = "dropXp()V", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
	private int create$dropXp(int i) {
		return LivingEntityExperienceDropCallback.EVENT.invoker().onLivingEntityExperienceDrop(i, attackingPlayer);
	}
}
