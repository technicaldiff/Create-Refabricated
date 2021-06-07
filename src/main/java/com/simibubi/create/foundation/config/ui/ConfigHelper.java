package com.simibubi.create.foundation.config.ui;

import java.util.Arrays;
import java.util.List;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.lib.config.Config;
import com.simibubi.create.lib.config.ConfigValue;

public class ConfigHelper {

//	private static final LoadingCache<String, EnumMap<ModConfig.Type, ModConfig>> configCache = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.SECONDS).build(
//			new CacheLoader<String, EnumMap<ModConfig.Type, ModConfig>>() {
//				@Override
//				public EnumMap<ModConfig.Type, ModConfig> load(@Nonnull String key) {
//					return findModConfigsUncached(key);
//				}
//			}
//	);

//	private static EnumMap<ModConfig.Type, ModConfig> findModConfigsUncached(String modID) {
//		ModContainer modContainer = ModList.get().getModContainerById(modID).orElseThrow(() -> new IllegalArgumentException("Unable to find ModContainer for id: " + modID));
//		EnumMap<ModConfig.Type, ModConfig> configs = ObfuscationReflectionHelper.getPrivateValue(ModContainer.class, modContainer, "configs");
//		return Objects.requireNonNull(configs);
//	}
	public static Config findConfigSpecFor(String name, String modID) {
		if (name.equals("client")) return AllConfigs.CLIENT.config;
		if (name.equals("server")) return AllConfigs.SERVER.config;
		if (name.equals("common")) return AllConfigs.COMMON.config;
		return null;
	}

	public static Config findConfigSpecFor(ConfigPath path, String modID) {
//		if (!modID.equals(Create.ID))
//			return configCache.getUnchecked(modID).get(type).getSpec();
		List<String> paths = Arrays.asList(path.getPath());
		if (paths.contains("client")) return AllConfigs.CLIENT.config;
		if (paths.contains("server")) return AllConfigs.SERVER.config;
		if (paths.contains("common")) return AllConfigs.COMMON.config;
//		switch (type) {
//			case COMMON:
//				return AllConfigs.COMMON.specification;
//			case CLIENT:
//				return AllConfigs.CLIENT.specification;
//			case SERVER:
//				return AllConfigs.SERVER.specification;
//		}

		return null;
	}

	//Directly set a value
	public static <T> void setConfigValue(ConfigPath path, String value) throws InvalidValueException {
		Config spec = findConfigSpecFor(path, path.getModID());
		List<String> pathList = Arrays.asList(path.getPath());
//		ForgeConfigSpec.ValueSpec valueSpec = spec.getRaw(pathList);
		ConfigValue<T> configValue = (ConfigValue<T>) spec.get(pathList.get(pathList.size() - 1));
		T v = (T) CConfigureConfigPacket.deserialize(configValue.get(), value);
		if (!configValue.fitsConstraint(v))
			throw new InvalidValueException();

		configValue.set(v);
	}

	//Add a value to the current UI's changes list
	public static <T> void setValue(String path, ConfigValue<T> configValue, T value) {
		if (value.equals(configValue.get())) {
			ConfigScreen.changes.remove(path);
		} else {
			ConfigScreen.changes.put(path, value);
		}
	}

	//Get a value from the current UI's changes list or the config value, if its unchanged
	public static <T> T getValue(String path, ConfigValue<T> configValue) {
		//noinspection unchecked
		return (T) ConfigScreen.changes.getOrDefault(path, configValue.get());
	}

	public static class ConfigPath {
		private String modID = Create.ID;
//		private ModConfig.Type type = ModConfig.Type.CLIENT;
		private String[] path;

		public static ConfigPath parse(String string) {
			ConfigPath cp = new ConfigPath();
			String p = string;
			int index = string.indexOf(":");
			if (index >= 0) {
				p = string.substring(index + 1);
				if (index >= 1) {
					cp.modID = string.substring(0, index);
				}
			}
			String[] split = p.split("\\.");
//			try {
//				cp.type = ModConfig.Type.valueOf(split[0].toUpperCase(Locale.ROOT));
//			} catch (Exception e) {
//				throw new IllegalArgumentException("path must start with either 'client.', 'common.' or 'server.'");
//			}

			cp.path = new String[split.length - 1];
			System.arraycopy(split, 1, cp.path, 0, cp.path.length);

			return cp;
		}

		public ConfigPath setID(String modID) {
			this.modID = modID;
			return this;
		}

//		public ConfigPath setType(ModConfig.Type type) {
//			this.type = type;
//			return this;
//		}

		public ConfigPath setPath(String[] path) {
			this.path = path;
			return this;
		}

		public String getModID() {
			return modID;
		}

//		public ModConfig.Type getType() {
//			return type;
//		}

		public String[] getPath() {
			return path;
		}
	}

	public static class InvalidValueException extends Exception {}
}
