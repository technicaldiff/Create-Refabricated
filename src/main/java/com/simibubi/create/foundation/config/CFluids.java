package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

public class CFluids extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.LEVEL;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder fluids = group(0, "fluids", null, CServer.Comments.fluids);
	public static final ConfigValue<Integer> fluidTankCapacity = i(8, 1, "fluidTankCapacity", fluids, Comments.buckets, Comments.fluidTankCapacity);
	public static final ConfigValue<Integer> fluidTankMaxHeight = i(32, 1, "fluidTankMaxHeight", fluids, Comments.blocks, Comments.fluidTankMaxHeight);
	public static final ConfigValue<Integer> mechanicalPumpRange =
		i(16, 1, "mechanicalPumpRange", fluids, Comments.blocks, Comments.mechanicalPumpRange);

	public static final ConfigValue<Integer> hosePulleyBlockThreshold = i(10000, -1, "hosePulleyBlockThreshold", fluids, Comments.blocks,
		Comments.toDisable, Comments.hosePulleyBlockThreshold);
	public static final ConfigValue<Integer> hosePulleyRange = i(128, 1, "hosePulleyRange", fluids, Comments.blocks, Comments.hosePulleyRange);

	@Override
	public String getName() {
		return "fluids";
	}

	private static class Comments {
		static String blocks = "[in Blocks]";
		static String buckets = "[in Buckets]";
		static String fluidTankCapacity = "The amount of liquid a tank can hold per block.";
		static String fluidTankMaxHeight = "The maximum height a fluid tank can reach.";
		static String mechanicalPumpRange =
			"The maximum distance a mechanical pump can push or pull liquids on either side.";

		static String hosePulleyRange = "The maximum distance a hose pulley can draw fluid blocks from.";
		static String toDisable = "[-1 to disable this behaviour]";
		static String hosePulleyBlockThreshold =
			"The minimum amount of fluid blocks the hose pulley needs to find before deeming it an infinite source.";
	}

}
