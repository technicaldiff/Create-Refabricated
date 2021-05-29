package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

public class CSchematics extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.LEVEL;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder schematics = group(0, "schematics", null, CServer.Comments.schematics);
	public static final ConfigValue<Integer> maxSchematics = i(10, 1, "maxSchematics", schematics, Comments.maxSchematics);
	public static final ConfigValue<Integer> maxTotalSchematicSize = i(256, 16, "maxSchematics", schematics, Comments.kb, Comments.maxSize);
	public static final ConfigValue<Integer> maxSchematicPacketSize =
		i(1024, 256, 32767, "maxSchematicPacketSize", schematics, Comments.b, Comments.maxPacketSize);
	public static final ConfigValue<Integer> schematicIdleTimeout = i(600, 100, "schematicIdleTimeout", schematics, Comments.idleTimeout);

	public static final CategoryBuilder schematicannon = group(0, "schematicannon", schematics, "Schematicannon");
	public static final ConfigValue<Integer> schematicannonDelay = i(10, 1, "schematicannonDelay", schematicannon, Comments.delay);
	public static final ConfigValue<Integer> schematicannonSkips = i(10, 1, "schematicannonSkips", schematicannon, Comments.skips);
	public static final ConfigValue<Float> schematicannonGunpowderWorth =
		f(20, 0, 100, "schematicannonGunpowderWorth", schematicannon, Comments.gunpowderWorth);
	public static final ConfigValue<Float> schematicannonFuelUsage = f(0.05f, 0, 100, "schematicannonFuelUsage", schematicannon, Comments.fuelUsage);

	@Override
	public String getName() {
		return "schematics";
	}

	private static class Comments {
		static String kb = "[in KiloBytes]";
		static String b = "[in Bytes]";
		static String maxSchematics =
			"The amount of Schematics a player can upload until previous ones are overwritten.";
		static String maxSize = "The maximum allowed file size of uploaded Schematics.";
		static String maxPacketSize = "The maximum packet size uploaded Schematics are split into.";
		static String idleTimeout =
			"Amount of game ticks without new packets arriving until an active schematic upload process is discarded.";
		static String delay = "Amount of game ticks between shots of the cannon. Higher => Slower";
		static String skips = "Amount of block positions per tick scanned by a running cannon. Higher => Faster";
		static String gunpowderWorth = "% of Schematicannon's Fuel filled by 1 Gunpowder.";
		static String fuelUsage = "% of Schematicannon's Fuel used for each fired block.";
	}

}
