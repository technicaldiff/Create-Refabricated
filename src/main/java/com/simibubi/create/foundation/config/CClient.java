package com.simibubi.create.foundation.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;

import net.minecraft.util.math.MathHelper;

import com.simibubi.create.foundation.config.util.Validatable;

public class CClient implements Validatable {
	@Tooltip
	public boolean enableTooltips = true;

	@Tooltip
	public boolean enableOverstressedTooltip = true;

	@Tooltip
	public boolean explainRenderErrors = true;

	public float fanParticleDensity = .5f;

	@Tooltip
	public boolean enableRainbowDebug = true;

	@Tooltip
	public boolean experimentalRendering = true;

	@Tooltip
	public int overlayOffsetX = 20;

	@Tooltip
	public int overlayOffsetY = 0;

	@Tooltip
	public boolean smoothPlacementIndicator = false;

	@Override
	public void validate() throws ConfigData.ValidationException {
		fanParticleDensity = MathHelper.clamp(fanParticleDensity, 0f, 1f);
	}
}
