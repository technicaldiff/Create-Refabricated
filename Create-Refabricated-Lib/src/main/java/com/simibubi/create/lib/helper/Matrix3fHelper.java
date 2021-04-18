package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.Matrix3fExtensions;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.util.math.vector.Matrix3f;

public final class Matrix3fHelper {

	public static float[] writeMatrix(Matrix3f matrix) {
		return get(matrix).create$writeMatrix();
	}

	private static Matrix3fExtensions get(Matrix3f m) {
		return MixinHelper.cast(m);
	}

	private Matrix3fHelper() {}
}
