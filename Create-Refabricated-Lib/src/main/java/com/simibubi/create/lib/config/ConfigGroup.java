package com.simibubi.create.lib.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigGroup {
	public List<ConfigValue> configs = new ArrayList<>();
	public Config config;
	public int depth;
	public String name;
	public List<String> comments;


	public ConfigGroup(int depth, String name, String... comments) {
		this.depth = depth;
		this.name = name;
		this.comments = Arrays.asList(comments);
	}

	public void addConfigValue(ConfigValue value) {
		configs.add(value);
		config.set(value);
	}

	public ConfigValue getConfigValue(String key) {
		for (ConfigValue value : configs) {
			if (value.key.equals(key)) {
				return value;
			}
		}
		return null;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Config getConfig() {
		return config;
	}
}
