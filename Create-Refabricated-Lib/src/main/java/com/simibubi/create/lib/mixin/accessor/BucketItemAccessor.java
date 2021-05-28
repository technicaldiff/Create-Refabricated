package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;

@Mixin(BucketItem.class)
public interface BucketItemAccessor {
	@Accessor("containedBlock")
	public Fluid getContainedBlock();
}
