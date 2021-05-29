package com.simibubi.create.lib.utility;

import dev.inkwell.conrad.api.value.ValueKey;

public class ConfigValue<T> {
	ValueKey<T> key;

	public ConfigValue(ValueKey<T> key) {
		this.key = key;
	}

	public T get() {
		return key.getValue();
	}

	public float getF() {
		return (Float) key.getValue();
	}

	public void set(T value) {
		key.setValue(value);
	}
}
