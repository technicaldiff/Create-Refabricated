package com.simibubi.create.foundation.config;


import com.tterrag.registrate.fabric.EnvExecutor;

import net.fabricmc.api.EnvType;

public class AllConfigs {

//	static Map<ConfigBase, ModConfig.Type> configs = new HashMap<>();

	public static CClient CLIENT;
	public static CCommon COMMON;
	public static CServer SERVER;

//	private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
//		Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
//			T config = factory.get();
//			config.registerAll(builder);
//			return config;
//		});
//
//		T config = specPair.getLeft();
//		config.specification = specPair.getRight();
//		configs.put(config, side);
//		return config;
//	}

	public static void register() {
		EnvExecutor.runWhenOn(EnvType.CLIENT, () -> () -> CLIENT = new CClient());
		COMMON = new CCommon();
		SERVER = new CServer();

		SERVER.kinetics.stressValues.registerAll();
		COMMON.worldGen.registerAll();
//		CLIENT = register(CClient::new, ModConfig.Type.CLIENT);
//		COMMON = register(CCommon::new, ModConfig.Type.COMMON);
//		SERVER = register(CServer::new, ModConfig.Type.SERVER);
//
//		for (Entry<ConfigBase, Type> pair : configs.entrySet())
//			ModLoadingContext.get()
//				.registerConfig(pair.getValue(), pair.getKey().specification);
	}

//	public static void onLoad(ModConfig.Loading event) {
//		for (Entry<ConfigBase, Type> pair : configs.entrySet())
//			if (pair.getKey().specification == event.getConfig()
//				.getSpec())
//				pair.getKey()
//					.onLoad();
//	}

//	public static void onReload(ModConfig.Reloading event) {
//		for (Entry<ConfigBase, Type> pair : configs.entrySet())
//			if (pair.getKey().specification == event.getConfig()
//				.getSpec())
//				pair.getKey()
//					.onReload();
//	}
}
