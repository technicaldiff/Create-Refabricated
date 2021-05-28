package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;

import dev.inkwell.conrad.api.Config;
import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.GsonSerializer;


public class AllConfigs extends Config<JsonObject> {

	@Override
	public @NotNull ConfigSerializer<JsonObject> getSerializer() {
		return GsonSerializer.DEFAULT;
	}

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.USER;
	}

//	static Map<ConfigBase, ModConfig.Type> configs = new HashMap<>();

	public static CClient CLIENT = new CClient();
	public static CCommon COMMON = new CCommon();
	public static CServer SERVER = new CServer();

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
