package com.simibubi.create.foundation.config;

import com.simibubi.create.foundation.worldgen.AllWorldFeatures;
import com.simibubi.create.lib.config.Config;
import com.simibubi.create.lib.config.Configs;


public class CWorldGen extends ConfigBase {

	public Config config = new Config(Configs.PATH_TO_CONFIGS + getName());
	@Override
	public Config getConfig() {
		return config;
	}

	public ConfigBool disable = b(false, "disableWorldGen", Comments.disable);

	@Override
	protected void registerAll() {
		super.registerAll();
		AllWorldFeatures.fillConfig();
	}

	@Override
	public String getName() {
		return "worldgen.v" + AllWorldFeatures.forcedUpdateVersion;
	}

	private static class Comments {
		static String disable = "Prevents all worldgen added by Create from taking effect";
	}

}
