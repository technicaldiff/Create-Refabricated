package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public interface EntityAccessor {
	@Invoker("canBeRidden")
	boolean create$canBeRidden(Entity entity);

	@Invoker("getEntityString")
	String getEntityString();
}
