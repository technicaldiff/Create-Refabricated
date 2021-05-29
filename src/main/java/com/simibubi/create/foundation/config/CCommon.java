package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

public class CCommon extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.ROOT;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder common = group(0, "common", null, "Settings for both client and server");
	public static final ConfigValue<Boolean> logTeErrors = b(false, "logTeErrors", null, Comments.logTeErrors);
	public CWorldGen worldGen = new CWorldGen();

	@Override
	public String getName() {
		return "common";
	}

	public static class Comments {
		static String worldGen = "Modify Create's impact on your terrain";
		static String logTeErrors = "Forward caught TileEntityExceptions to the log at debug level.";
	}

}
