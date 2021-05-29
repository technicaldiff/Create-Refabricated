package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.foundation.worldgen.AllWorldFeatures;
import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;


public class CWorldGen extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.LEVEL;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder worldgen = group(0, "worldGen", null, CCommon.Comments.worldGen);
	public static final ConfigValue<Boolean> disable = b(false, "disableWorldGen", worldgen, Comments.disable);

	@Override
	protected void registerAll() {
		super.registerAll();
		AllWorldFeatures.fillConfig(worldgen);
	}

	@Override
	public String getName() {
		return "worldgen.v" + AllWorldFeatures.forcedUpdateVersion;
	}

	private static class Comments {
		static String disable = "Prevents all worldgen added by Create from taking effect";
	}

}
