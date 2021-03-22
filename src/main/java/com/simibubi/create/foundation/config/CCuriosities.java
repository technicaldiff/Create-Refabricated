package com.simibubi.create.foundation.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

import com.simibubi.create.foundation.config.util.Validatable;

public class CCuriosities implements Validatable {
	@ConfigEntry.Gui.Tooltip
	public int maxSymmetryWandRange = 50; // min 10

	@Override
	public void validate() {
		maxSymmetryWandRange = Math.max(maxSymmetryWandRange, 10);
	}
	//int zapperUndoLogLength = 10; // min 0, "The maximum amount of operations, a blockzapper can remember for undoing. (0 to disable undo)" NOT YET IMPLEMENTED UPSTREAM
}
