package com.simibubi.create.lib.helper;

import java.util.stream.Stream;

import com.simibubi.create.lib.mixin.accessor.SignTypeAccessor;

import net.minecraft.util.SignType;

public final class SignTypeHelper {
	// SignType.stream is client-side only, but this method is not
	public static Stream<SignType> stream() {
		return SignTypeAccessor.create$VALUES().stream();
	}

	private SignTypeHelper() {}
}
