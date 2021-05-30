package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.item.minecart.AbstractMinecartEntity;

@Mixin(AbstractMinecartEntity.class)
public interface AbstractMinecartEntityAccessor {
	@Invoker("getMaximumSpeed")
	double create$getMaximumSpeed();
}
