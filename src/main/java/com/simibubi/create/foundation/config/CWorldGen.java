package com.simibubi.create.foundation.config;

import com.simibubi.create.foundation.config.util.Validatable;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.util.math.MathHelper;

public class CWorldGen implements Validatable {
	public boolean disable = false; // "Prevents all worldgen added by Create from taking effect"

	@ConfigEntry.Gui.PrefixText()
	public int copperOreMinHeight = 40; // min 0,

	public int copperOreMaxHeight = 85; // min 0,
	public int copperOreClusterSize = 18; // min 0,
	public float copperOreFrequency = 2.0f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@ConfigEntry.Gui.PrefixText()
	public int weatheredLimestoneMinHeight = 10; // min 0,

	public int weatheredLimestoneMaxHeight = 30; // min 0,
	public int weatheredLimestoneClusterSize = 128; // min 0,
	public float weatheredLimestoneFrequency = 0.015625f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@ConfigEntry.Gui.PrefixText()
	public int zincOreMinHeight = 15; // min 0,

	public int zincOreMaxHeight = 70; // min 0,
	public int zincOreClusterSize = 14; // min 0,
	public float zincOreFrequency = 4.0f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@ConfigEntry.Gui.PrefixText()
	public int limestoneMinHeight = 30; // min 0,

	public int limestoneMaxHeight = 70; // min 0,
	public int limestoneClusterSize = 128; // min 0,
	public float limestoneFrequency = 0.015625f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@ConfigEntry.Gui.PrefixText()
	public int dolomiteMinHeight = 20; // min 0,

	public int dolomiteMaxHeight = 70; // min 0,
	public int dolomiteClusterSize = 128; // min 0,
	public float dolomiteFrequency = 0.015625f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@ConfigEntry.Gui.PrefixText()
	public int gabbroMinHeight = 20; // min 0,

	public int gabbroMaxHeight = 70; // min 0,
	public int gabbroClusterSize = 128; // min 0,
	public float gabbroFrequency = 0.015625f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@ConfigEntry.Gui.PrefixText()
	public int scoriaMinHeight = 0; // min 0,

	public int scoriaMaxHeight = 10; // min 0,
	public int scoriaClusterSize = 128; // min 0,
	public float scoriaFrequency = 0.03125f; // 0.0 ~ 512.0, Amount of clusters generated per Chunk. >1 to spawn multiple. <1 to make it a chance. #  0 to disable.

	@Override
	public void validate() throws ConfigData.ValidationException {
		copperOreMinHeight = Math.max(copperOreMinHeight, 0);
		copperOreMaxHeight = Math.max(copperOreMaxHeight, 0);
		copperOreClusterSize = Math.max(copperOreClusterSize, 0);
		copperOreFrequency = MathHelper.clamp(copperOreFrequency, 0f, 512f);
		weatheredLimestoneMinHeight = Math.max(weatheredLimestoneMinHeight, 0);
		// TODO: The rest of them
	}
}
