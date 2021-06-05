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

	public Config(File file) {
		configFile = file;
		properties = new Properties();
		try {
			if (!configFile.exists()) {
				configFile.createNewFile();
			}
			out = new FileOutputStream(file);
		} catch (IOException e) {
			LOGGER.fatal("There was an error initializing Create configs!", e);
		}
	}

	public Config(String pathToFile) {
		this(new File(pathToFile));
	}

	public void set(ConfigValue value) {
		properties.put(value.key, value.value);
		save();
	}

	public Object get(String key) {
		return properties.get(key);
	}

	public void save() {
		try {
			properties.store(out, null);
		} catch (IOException e) {
			LOGGER.fatal("There was an error saving Create configs!", e);
		}
	}
}
