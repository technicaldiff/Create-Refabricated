package com.simibubi.create.foundation.config;

import com.simibubi.create.lib.config.Config;
import com.simibubi.create.lib.config.ConfigGroup;

public class CCuriosities extends ConfigBase {
	public ConfigGroup curiosities = group(0, "curiosities", CServer.Comments.curiosities);
	public ConfigInt maxSymmetryWandRange = i(50, 10, "maxSymmetryWandRange", Comments.symmetryRange);
	public ConfigInt placementAssistRange = i(12, 3, "placementAssistRange", Comments.placementRange);
	public ConfigInt maxAirInBacktank = i(900, 1, "maxAirInBacktank", Comments.maxAirInBacktank);

	public ConfigInt maxExtendoGripActions = i(1000, 0, "maxExtendoGripActions", Comments.maxExtendoGripActions);
	public ConfigInt maxPotatoCannonShots = i(200, 0, "maxPotatoCannonShots", Comments.maxPotatoCannonShots);

//	public ConfigInt zapperUndoLogLength = i(10, 0, "zapperUndoLogLength", Comments.zapperUndoLogLength); NYI

	public Config config = new Config(getName());
	@Override
	public Config getConfig() {
		return config;
	}

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
		static String maxExtendoGripActions =
			"Amount of free Extendo Grip actions provided by one filled Copper Backtank. Set to 0 makes Extendo Grips unbreakable";
		static String maxPotatoCannonShots =
			"Amount of free Potato Cannon shots provided by one filled Copper Backtank. Set to 0 makes Potato Cannons unbreakable";
//		static String zapperUndoLogLength = "The maximum amount of operations, a blockzapper can remember for undoing. (0 to disable undo)";
	}

}
