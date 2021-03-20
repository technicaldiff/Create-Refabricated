package com.simibubi.create.foundation.utility.extensions;

import java.util.stream.Stream;

import com.simibubi.create.foundation.mixin.accessor.SignTypeAccessor;

import net.minecraft.util.SignType;

public interface SignTypeExtensions {
	public static Stream<SignType> stream() {
		return SignTypeAccessor.getValues().stream();
	}
}
