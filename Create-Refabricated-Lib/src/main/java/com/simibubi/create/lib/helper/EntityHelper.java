package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.EntityExtensions;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;

public final class EntityHelper {
	public static final String EXTRA_DATA_KEY = "create_ExtraData";

	public static CompoundTag getExtraCustomData(Entity entity) {
		return ((EntityExtensions) entity).create$getExtraCustomData();
	}

	private EntityHelper() {}
}
