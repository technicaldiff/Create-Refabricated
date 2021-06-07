package com.simibubi.create.lib.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {
	public File configFile;
	public List<ConfigGroup> groups = new ArrayList<>();
	public OutputStream out;
	public Properties properties;
	private static final Logger LOGGER = LogManager.getLogger();
	public String name;

	public Config(String name) {
		this.name = name;
		configFile = new File(Configs.PATH_TO_CONFIGS + name + ".properties");
		properties = new Properties();
		try {
			if (!configFile.exists()) {
				configFile.createNewFile();
			}
			out = new FileOutputStream(configFile);
		} catch (IOException e) {
			LOGGER.fatal("There was an error initializing Create's [{}] config!", name, e);
		}
	}

	public void set(ConfigValue value) {
		properties.put(value.key, value.value.toString());
		save();
	}

	public Object get(String key) {
		return properties.get(key);
	}

	public List<ConfigValue> getAllValues() {
		List<ConfigValue> values = new ArrayList<>();
		for (ConfigGroup group : groups) {
			for (ConfigValue value : group.configs) {
				values.add(value);
			}
		}
		return values;
	}

	public void save() {
		try {
			properties.store(out, null);
		} catch (IOException e) {
			LOGGER.fatal("There was an error saving Create's [{}] config!", name, e);
		}
	}
}
