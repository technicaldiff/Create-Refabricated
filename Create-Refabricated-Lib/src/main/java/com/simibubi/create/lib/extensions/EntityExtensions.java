package com.simibubi.create.lib.extensions;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.Collection;

public interface EntityExtensions {
	CompoundNBT create$getExtraCustomData();

	Collection<ItemEntity> captureDrops();

	Collection<ItemEntity> captureDrops(Collection<ItemEntity> value);
}
