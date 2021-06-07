package com.simibubi.create.lib.extensions;

import org.jetbrains.annotations.NotNull;

import net.minecraft.util.math.vector.Matrix3f;

public interface Matrix3fExtensions {
	float[] create$writeMatrix();

	void create$set(@NotNull Matrix3f other);
}
