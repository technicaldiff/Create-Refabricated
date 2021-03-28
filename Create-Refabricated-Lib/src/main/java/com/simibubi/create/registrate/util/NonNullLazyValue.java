package com.simibubi.create.registrate.util;

import net.minecraft.util.Lazy;

import com.simibubi.create.registrate.util.nullness.NonNullSupplier;
import com.simibubi.create.registrate.util.nullness.NonnullType;

public class NonNullLazyValue<T> extends Lazy<T> implements NonNullSupplier<T> {

	public NonNullLazyValue(NonNullSupplier<T> supplier) {
		super(supplier);
	}

	@Override
	public @NonnullType T get() {
		return get();
	}
}
