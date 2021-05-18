package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.event.PlayerStartTrackingCallback;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.TrackedEntity;

@Mixin(TrackedEntity.class)
public abstract class TrackedEntityMixin {
	@Shadow
	@Final
	private Entity trackedEntity;

	@Inject(method = "track(Lnet/minecraft/entity/player/ServerPlayerEntity;)V", at = @At("TAIL"))
	private void create$track(ServerPlayerEntity serverPlayerEntity, CallbackInfo ci) {
		PlayerStartTrackingCallback.EVENT.invoker().onPlayerStartTracking(serverPlayerEntity, trackedEntity);
	}
}
