package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

public class CLogistics extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.LEVEL;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder logistics = group(0, "logistics", null, CServer.Comments.logistics);
	public static final ConfigValue<Integer> defaultExtractionLimit = i(64, 1, 64, "defaultExtractionLimit", logistics, Comments.defaultExtractionLimit);
	public static final ConfigValue<Integer> defaultExtractionTimer = i(8, 1, "defaultExtractionTimer", logistics, Comments.defaultExtractionTimer);
	public static final ConfigValue<Integer> psiTimeout = i(20, 1, "psiTimeout", logistics, Comments.psiTimeout);
	public static final ConfigValue<Integer> mechanicalArmRange = i(5, 1, "mechanicalArmRange", logistics, Comments.mechanicalArmRange);
	public static final ConfigValue<Integer> linkRange = i(128, 1, "linkRange", logistics, Comments.linkRange);

	@Override
	public String getName() {
		return "logistics";
	}

	private static class Comments {
		static String defaultExtractionLimit =
			"The maximum amount of items a funnel pulls at a time without an applied filter.";
		static String defaultExtractionTimer =
			"The amount of ticks a funnel waits between item transferrals, when it is not re-activated by redstone.";
		static String linkRange = "Maximum possible range in blocks of redstone link connections.";
		static String psiTimeout =
			"The amount of ticks a portable storage interface waits for transfers until letting contraptions move along.";
		static String mechanicalArmRange = "Maximum distance in blocks a Mechanical Arm can reach across.";
	}

}
