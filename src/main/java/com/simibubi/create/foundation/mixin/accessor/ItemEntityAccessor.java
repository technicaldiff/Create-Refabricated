package com.simibubi.create.foundation.mixin.accessor;

import net.minecraft.entity.ItemEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemEntity.class)
public interface ItemEntityAccessor {
	@Accessor("age")
	public int getAge();
	@Accessor("age")
	public void setAge(int age);
}
