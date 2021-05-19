package com.simibubi.create.lib.extensions;

import java.util.Collection;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;

public interface EntityExtensions {
	CompoundNBT create$getExtraCustomData();

	Collection<ItemEntity> create$captureDrops();

	Collection<ItemEntity> create$captureDrops(Collection<ItemEntity> value);
}
