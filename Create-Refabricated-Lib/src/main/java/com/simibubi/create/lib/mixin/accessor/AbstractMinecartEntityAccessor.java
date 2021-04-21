package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.entity.item.minecart.AbstractMinecartEntity;

import net.minecraft.util.WeightedSpawnerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractMinecartEntity.class)
public interface AbstractMinecartEntityAccessor {
	@Invoker("getMaximumSpeed")
	double create$getMaximumSpeed();
}
