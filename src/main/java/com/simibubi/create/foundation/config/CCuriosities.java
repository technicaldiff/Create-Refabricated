package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

public class CCuriosities extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.LEVEL;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder curiosities = group(0, "curiosities", null, CServer.Comments.curiosities);
	public static final ConfigValue<Integer> maxSymmetryWandRange = i(50, 10, "maxSymmetryWandRange", curiosities, Comments.symmetryRange);
	public static final ConfigValue<Integer> placementAssistRange = i(12, 3, "placementAssistRange", curiosities, Comments.placementRange);
	public static final ConfigValue<Integer> maxAirInBacktank = i(900, 1, "maxAirInBacktank", curiosities, Comments.maxAirInBacktank);
//	public static final ConfigInt zapperUndoLogLength = i(10, 0, "zapperUndoLogLength", curiosities, Comments.zapperUndoLogLength); NYI

	@Override
	public String getName() {
		return "curiosities";
	}

	private static class Comments {
		static String symmetryRange = "The Maximum Distance to an active mirror for the symmetry wand to trigger.";
		static String maxAirInBacktank =
			"The Maximum volume of Air that can be stored in a backtank = Seconds of underwater breathing";
		static String placementRange =
			"The Maximum Distance a Block placed by Create's placement assist will have to its interaction point.";
//		static String zapperUndoLogLength = "The maximum amount of operations, a blockzapper can remember for undoing. (0 to disable undo)";
	}

}
