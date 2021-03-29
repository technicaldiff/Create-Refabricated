package com.simibubi.create.lib.extensions;

import java.util.stream.Stream;

import com.simibubi.create.lib.mixin.accessor.SignTypeAccessor;

import net.minecraft.util.SignType;

public interface SignTypeExtensions {
	static Stream<SignType> stream() {
		return SignTypeAccessor.getValues().stream();
	}
}
