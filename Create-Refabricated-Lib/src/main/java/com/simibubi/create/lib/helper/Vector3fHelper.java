package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.Vector3fAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.util.math.vector.Vector3f;

public final class Vector3fHelper {
	public static void setX(Vector3f vector, float x) {
		get(vector).create$x(x);
	}

	public static void setY(Vector3f vector, float y) {
		get(vector).create$y(y);
	}

	public static void setZ(Vector3f vector, float z) {
		get(vector).create$z(z);
	}

	private static Vector3fAccessor get(Vector3f vector) {
		return MixinHelper.cast(vector);
	}

	private Vector3fHelper() {}
}
