package com.simibubi.create.lib.mixin;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.EntityShapeContext;
import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.extensions.EntityShapeContextExtensions;

@Mixin(EntityShapeContext.class)
public class EntityShapeContextMixin implements EntityShapeContextExtensions {
	@Unique
	private @Nullable Entity cachedEntity;

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/entity/Entity;)V")
	private void onTailEntityInit(Entity entity, CallbackInfo ci) {
		cachedEntity = entity;
	}

	@Override
	public @Nullable Entity getCachedEntity() {
		return cachedEntity;
	}
}
