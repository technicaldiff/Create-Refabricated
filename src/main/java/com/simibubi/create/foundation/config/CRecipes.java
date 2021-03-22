package com.simibubi.create.foundation.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;

import com.simibubi.create.foundation.config.util.Validatable;

public class CRecipes implements Validatable {
	@Tooltip
	public boolean allowShapelessInMixer = true;
	@Tooltip
	public boolean allowShapedSquareInPress = true;
	@Tooltip
	public boolean allowRegularCraftingInCrafter = true;
	@Tooltip
	public boolean allowStonecuttingOnSaw = true;
	@Tooltip
	public boolean allowWoodcuttingOnSaw = true;
	@Tooltip
	public int lightSourceCountForRefinedRadiance = 10; // min 1
	@Tooltip
	public boolean enableRefinedRadianceRecipe = true;
	@Tooltip
	public boolean enableShadowSteelRecipe = true;

	@Override
	public void validate() throws ConfigData.ValidationException {
		lightSourceCountForRefinedRadiance = Math.max(lightSourceCountForRefinedRadiance, 1);
	}
}
