package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.entity.ItemEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemEntity.class)
public interface ItemEntityAccessor {
	@Accessor("age")
	int getAge();

	@Accessor("age")
	void setAge(int age);
}
