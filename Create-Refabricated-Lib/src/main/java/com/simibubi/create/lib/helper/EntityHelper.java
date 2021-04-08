package com.simibubi.create.lib.helper;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;

import com.simibubi.create.lib.extensions.EntityExtensions;

public final class EntityHelper {
	public static final String EXTRA_DATA_KEY = "create_lib_ExtraData";

	public static CompoundTag getExtraCustomData(Entity entity) {
		return ((EntityExtensions) entity).create$getExtraCustomData();
	}

	private EntityHelper() {}
}
