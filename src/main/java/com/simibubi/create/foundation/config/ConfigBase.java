package com.simibubi.create.foundation.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.simibubi.create.lib.config.Config;
import com.simibubi.create.lib.config.ConfigGroup;
import com.simibubi.create.lib.config.ConfigValue;


public abstract class ConfigBase {

	public abstract Config getConfig();

//	public ForgeConfigSpec specification;

	protected int depth;
	protected List<CValue<?, ?>> allValues;
	protected List<ConfigBase> children;

	protected void registerAll() {
//		for (CValue<?, ?> cValue : allValues)
//			cValue.register(builder);
	}

	public void onLoad() {
		if (children != null)
			children.forEach(ConfigBase::onLoad);
	}

	public void onReload() {
		if (children != null)
			children.forEach(ConfigBase::onReload);
	}

	public abstract String getName();

//	@FunctionalInterface
//	protected static interface IValueProvider<V, T extends ConfigValue<V>>
//		extends Function<ForgeConfigSpec.Builder, T> {
//	}

	protected ConfigBool b(boolean current, String name, String... comment) {
		ConfigBool result = new ConfigBool(name, current, getCurrentConfigGroup());
		result.addComments(comment);
		return result;
	}

	protected ConfigFloat f(float current, float min, float max, String name, String... comment) {
		ConfigFloat result = new ConfigFloat(name, current, getCurrentConfigGroup());
		result.addComments(comment);
		return result;
	}

	protected ConfigFloat f(float current, float min, String name, String... comment) {
		return f(current, min, Float.MAX_VALUE, name, comment);
	}

	protected ConfigInt i(int current, int min, int max, String name, String... comment) {
		ConfigInt result = new ConfigInt(name, current, getCurrentConfigGroup());
		result.addComments(comment);
		return result;
	}

	protected ConfigInt i(int current, int min, String name, String... comment) {
		return i(current, min, Integer.MAX_VALUE, name, comment);
	}

	protected <T extends Enum<T>> ConfigEnum<T> e(T defaultValue, String name, String... comment) {
		return new ConfigEnum<T>(name, defaultValue, getCurrentConfigGroup());
	}

	protected ConfigGroupWrapper group(int depth, String name, String... comment) {
		ConfigGroupWrapper group = new ConfigGroupWrapper(name, depth, comment);
		currentConfigGroup = group;
		return group;
	}

	protected <T extends ConfigBase> T nested(int depth, Supplier<T> constructor, String... comment) {
		T config = constructor.get();
		new ConfigGroupWrapper(config.getName(), depth, comment);
		new CValue<Boolean, ConfigBool>(config.getName(), builder -> {
			config.depth = depth;
			config.registerAll(builder);
			if (config.depth > depth)
				builder.pop(config.depth - depth);
			return null;
		});
		if (children == null)
			children = new ArrayList<>();
		children.add(config);
		return config;
	}

	public class CValue<V, T extends ConfigValue<V>> {
		protected ConfigValue<V> value;
		protected String name;
//		private IValueProvider<V, T> provider;

//		public CValue(String name, IValueProvider<V, T> provider, String... comment) {
//			this.name = name;
//			this.provider = builder -> {
//				addComments(builder, comment);
//				return provider.apply(builder);
//			};
//			if (allValues == null)
//				allValues = new ArrayList<>();
//			allValues.add(this);
//		}

//		public void addComments(Builder builder, String... comment) {
//			if (comment.length > 0) {
//				String[] comments = new String[comment.length + 1];
//				comments[0] = "";
//				System.arraycopy(comment, 0, comments, 1, comment.length);
//				builder.comment(comments);
//			} else
//				builder.comment("");
//		}

//		public void register(ForgeConfigSpec.Builder builder) {
//			value = provider.apply(builder);
//		}

		public V get() {
			return value.get();
		}

		public void set(V value) {
			this.value.set(value);
		}

		public String getName() {
			return name;
		}
	}

	public static class ConfigWrapper extends Config {
		public ConfigWrapper(File file) {
			super(file);
			currentConfig = this;
			currentConfigGroup = null;
		}

		public ConfigWrapper(String pathToFile) {
			super(pathToFile);
			currentConfig = this;
			currentConfigGroup = null;
		}
	}

	public static ConfigGroupWrapper currentConfigGroup;
	public static ConfigWrapper currentConfig;

	public static ConfigGroupWrapper getCurrentConfigGroup() {

	}

	/**
	 * Marker for config subgroups
	 */
	public static class ConfigGroupWrapper extends ConfigGroup {

//		private int groupDepth;
//		private String[] comment;

		public ConfigGroupWrapper(String name, int depth, String... comments) {
			super(depth, name, comments);
			currentConfigGroup = this;
		}

//		public ConfigGroup(String name, int depth, String... comment) {
//			super(name, builder -> null, comment);
//			groupDepth = depth;
//			this.comment = comment;
//		}

//		@Override
//		public void register(Builder builder) {
//			if (depth > groupDepth)
//				builder.pop(depth - groupDepth);
//			depth = groupDepth;
//			addComments(builder, comment);
//			builder.push(getName());
//			depth++;
//		}

	}

	public static class ConfigBool extends ConfigValue<Boolean> {
		public ConfigBool(String key, Boolean value, ConfigGroupWrapper group) {
			super(key, value, group);
		}
	}

	public static class ConfigEnum<T> extends ConfigValue<T> {
		public ConfigEnum(String key, T value, ConfigGroupWrapper group) {
			super(key, value, group);
		}
	}

	public static class ConfigFloat extends ConfigValue<Float> {
		public ConfigFloat(String key, Float value, ConfigGroupWrapper group) {
			super(key, value, group);
		}
	}

	public static class ConfigInt extends ConfigValue<Integer> {
		public ConfigInt(String key, Integer value, ConfigGroupWrapper group) {
			super(key, value, group);
		}
	}

//	public class ConfigBool extends CValue<Boolean, BooleanValue> {
//
//		public ConfigBool(String name, boolean def, String... comment) {
//			super(name, builder -> builder.define(name, def), comment);
//		}
//	}
//
//	public class ConfigEnum<T extends Enum<T>> extends CValue<T, EnumValue<T>> {
//
//		public ConfigEnum(String name, T defaultValue, String[] comment) {
//			super(name, builder -> builder.defineEnum(name, defaultValue), comment);
//		}
//
//	}
//
//	public class ConfigFloat extends CValue<Double, DoubleValue> {
//
//		public ConfigFloat(String name, float current, float min, float max, String... comment) {
//			super(name, builder -> builder.defineInRange(name, current, min, max), comment);
//		}
//
//		public float getF() {
//			return get().floatValue();
//		}
//	}
//
//	public class ConfigInt extends CValue<Integer, IntValue> {
//
//		public ConfigInt(String name, int current, int min, int max, String... comment) {
//			super(name, builder -> builder.defineInRange(name, current, min, max), comment);
//		}
//	}

}
