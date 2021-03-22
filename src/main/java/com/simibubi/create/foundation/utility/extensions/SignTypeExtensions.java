package com.simibubi.create.foundation.utility.extensions;

import java.util.stream.Stream;

import net.minecraft.util.SignType;

import com.simibubi.create.foundation.mixin.accessor.SignTypeAccessor;

public interface SignTypeExtensions {
	public static Stream<SignType> stream() {
		return SignTypeAccessor.getValues().stream();
	}
}
