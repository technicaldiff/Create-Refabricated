package com.simibubi.create.lib.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigValue<T> {
	public T value;
	public T defaultValue;
	public ConfigGroup group;
	public String key;
	public Constraint<T> constraint;
	public List<String> comments = new ArrayList<>();
	// only for number-based values
	public T min;
	public T max;

	public ConfigValue(String key, T value, ConfigGroup group) {
		this.value = value;
		this.defaultValue = value;
		this.group = group;
		this.key = key;
		this.group.addConfigValue(this);
	}

	public T get() {
		return value;
	}

	public boolean fitsConstraints(Constraint<T> constraint) {return constraint.fits(value, min, max);}

	public void set(T value) {
		if (fitsConstraints(constraint)) {
			this.value = value;
		} else {
			this.value = defaultValue;
		}
		updateStoredValue();
	}

	public void updateStoredValue() {
		group.config.set(this);
	}

	public void addComment(String comment) {
		comments.add(comment);
	}

	public void addComments(String... comments) {
		for (String comment : comments) {
			addComment(comment);
		}
	}

	public void setConstraint(Constraint<T> constraint) {
		this.constraint = constraint;
	}

	@FunctionalInterface
	public interface Constraint<T> {
		boolean fits(T value, T min, T max);
	}
}
