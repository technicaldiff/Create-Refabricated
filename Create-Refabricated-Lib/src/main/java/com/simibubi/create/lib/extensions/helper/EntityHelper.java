package com.simibubi.create.lib.extensions.helper;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;

import com.simibubi.create.lib.extensions.EntityExtensions;

public class EntityHelper {
	public static final String EXTRA_DATA_KEY = "create_lib_ExtraData";

	public static CompoundTag getExtraCustomData(Entity entity) {
		return ((EntityExtensions) entity).getExtraCustomData();
	}
}
