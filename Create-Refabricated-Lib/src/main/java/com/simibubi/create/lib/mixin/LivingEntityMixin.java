package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.event.LivingEntityExperienceDropCallback;
import com.simibubi.create.lib.event.LivingEntityTickCallback;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow protected PlayerEntity attackingPlayer;

	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tick()V"))
	private void create$tick(CallbackInfo ci) {
		LivingEntityTickCallback.EVENT.invoker().onLivingEntityTick((LivingEntity) (Object) this);
	}

	@ModifyVariable(method = "dropXp()V", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
	private int create$dropXp(int i) {
		return LivingEntityExperienceDropCallback.EVENT.invoker().onLivingEntityExperienceDrop(i, attackingPlayer);
	}
}
