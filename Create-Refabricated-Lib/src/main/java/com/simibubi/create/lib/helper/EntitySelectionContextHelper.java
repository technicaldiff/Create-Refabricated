package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.EntitySelectionContextExtensions;

import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.shapes.EntitySelectionContext;
import net.minecraft.util.math.shapes.ISelectionContext;

public final class EntitySelectionContextHelper {
	public static Entity getEntity(EntitySelectionContext context) {
		return MixinHelper.<EntitySelectionContextExtensions>cast(context).create$getCachedEntity();
	}

	public static Entity getEntity(ISelectionContext context) {
		if (context instanceof EntitySelectionContext) {
			return getEntity((EntitySelectionContext) context);
		}
		return null;
	}

	private EntitySelectionContextHelper() {}
}
