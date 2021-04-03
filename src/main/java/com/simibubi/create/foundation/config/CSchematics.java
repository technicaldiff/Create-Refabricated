// PORTED CREATE SOURCE

package com.simibubi.create.foundation.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;

import net.minecraft.util.math.MathHelper;

import com.simibubi.create.foundation.config.util.Validatable;

public class CSchematics implements Validatable {
	@Tooltip
	public int maxSchematics = 10; // min 1
	@Tooltip
	public int maxTotalSchematicSize = 256; // min 16
	@Tooltip
	public int maxSchematicPacketSize = 1024; // min 256, max 32767
	@Tooltip
	public int schematicIdleTimeout = 600; // min 100
	// schematicannon group         public ConfigGroup schematicannon = group(0, "schematicannon", "Schematicannon");
	@Tooltip(count = 2)
	public int schematicannonDelay = 10; // min 1
	@Tooltip(count = 2)
	public int schematicannonSkips = 10; // min 1
	@Tooltip
	public float schematicannonGunpowderWorth = 20f; // min 0, max 100
	@Tooltip
	public float schematicannonFuelUsage = 0.05f; // min 0, max 100

	@Override
	public void validate() throws ConfigData.ValidationException {
		maxSchematics = Math.max(maxSchematics, 1);
		maxTotalSchematicSize = Math.max(maxTotalSchematicSize, 16);
		maxSchematicPacketSize = MathHelper.clamp(maxSchematicPacketSize, 256, 32767);
		schematicIdleTimeout = Math.max(schematicIdleTimeout, 100);
		schematicannonDelay = Math.max(schematicannonDelay, 1);
		schematicannonSkips = Math.max(schematicannonSkips, 1);
		schematicannonGunpowderWorth = MathHelper.clamp(schematicannonGunpowderWorth, 0f, 100f);
		schematicannonFuelUsage = MathHelper.clamp(schematicannonFuelUsage, 0f, 100f);
	}
}
