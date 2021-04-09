package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.ItemEntity;

@Mixin(ItemEntity.class)
public interface ItemEntityAccessor {
	@Accessor("age")
	int create$age();

	@Accessor("age")
	void create$age(int age);
}
