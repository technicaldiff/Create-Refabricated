package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.EntitySelectionContextExtensions;

import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.shapes.ISelectionContext;

public final class EntitySelectionContextHelper {

	public static Entity getEntity(ISelectionContext context) {
		return MixinHelper.<EntitySelectionContextExtensions>cast(context).create$getCachedEntity();
	}

	private EntitySelectionContextHelper() {}
}
