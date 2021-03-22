package com.simibubi.create.foundation.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;

import net.minecraft.util.math.MathHelper;

import com.simibubi.create.foundation.config.util.Validatable;

public class CLogistics implements Validatable {
	@Tooltip
	public int defaultExtractionLimit = 64; // min 1, max 64
	@Tooltip
	public int defaultExtractionTimer = 8; // min 1
	@Tooltip
	public int psiTimeout = 20; // min 1
	@Tooltip
	public int mechanicalArmRange = 5; // min 1
	@Tooltip
	public int linkRange = 128; // min 1

	@Override
	public void validate() throws ConfigData.ValidationException {
		defaultExtractionLimit = MathHelper.clamp(defaultExtractionLimit, 1, 64);
		defaultExtractionTimer = Math.max(defaultExtractionTimer, 1);
		psiTimeout = Math.max(psiTimeout, 1);
		mechanicalArmRange = Math.max(mechanicalArmRange, 1);
		linkRange = Math.max(linkRange, 1);
	}
}
