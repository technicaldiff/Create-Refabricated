package com.simibubi.create.foundation.mixin;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.EntityShapeContext;
import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.foundation.EntityShapeContextMixinAccessor;

@Mixin(EntityShapeContext.class)
public class EntityShapeContextMixin implements EntityShapeContextMixinAccessor {
public @Nullable Entity entity2;

		@Inject(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At("RETURN"))
		protected void EntityShapeContext(Entity entity, CallbackInfo ci) {
			entity2 = entity;
		}
		@Override
		public @Nullable Entity getEntity() {
			return entity2;
		}
}
