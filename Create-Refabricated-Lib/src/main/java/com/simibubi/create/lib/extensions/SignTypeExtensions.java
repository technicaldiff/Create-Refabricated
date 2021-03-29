package com.simibubi.create.lib.extensions;

import java.util.stream.Stream;

import net.minecraft.util.SignType;

import com.simibubi.create.lib.mixin.accessor.SignTypeAccessor;

public interface SignTypeExtensions {
	static Stream<SignType> stream() {
		return SignTypeAccessor.getValues().stream();
	}
}
