package com.simibubi.create.foundation.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

@Environment(EnvType.CLIENT)
public class CClient extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.ROOT;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder client = group(0, "client", null,
		"Client-only settings"); // removed: " - If you're looking for general settings, look inside your worlds serverconfig folder!" - not true anymore
	public static final ConfigValue<Boolean> tooltips = b(true, "enableTooltips", client, "Show item descriptions on Shift and controls on Ctrl.");
	public static final ConfigValue<Boolean> enableOverstressedTooltip =
		b(true, "enableOverstressedTooltip", client, "Display a tooltip when looking at overstressed components.");
	public static final ConfigValue<Boolean> explainRenderErrors =
		b(false, "explainRenderErrors", client, "Log a stack-trace when rendering issues happen within a moving contraption.");
	public static final ConfigValue<Float> fanParticleDensity = f(.5f, 0, 1, "fanParticleDensity", client);
	public static final ConfigValue<Boolean> rainbowDebug =
		b(true, "enableRainbowDebug", client, "Show colourful debug information while the F3-Menu is open.");
	public static final ConfigValue<Boolean> experimentalRendering =
		b(true, "experimentalRendering", client, "Use modern OpenGL features to drastically increase performance.");
	public static final ConfigValue<Integer> overlayOffsetX = i(20, Integer.MIN_VALUE, Integer.MAX_VALUE, "overlayOffsetX", client,
		"Offset the overlay from goggle- and hover- information by this many pixels on the X axis; Use /create overlay");
	public static final ConfigValue<Integer> overlayOffsetY = i(0, Integer.MIN_VALUE, Integer.MAX_VALUE, "overlayOffsetY", client,
		"Offset the overlay from goggle- and hover- information by this many pixels on the Y axis; Use /create overlay");

	public static final ConfigValue<Integer> mainMenuConfigButtonRow = i(2, 0, 4, "mainMenuConfigButtonRow", client,
		"Choose the menu row that the Create config button appears on in the main menu",
		"Set to 0 to disable the button altogether");
	public static final ConfigValue<Integer> mainMenuConfigButtonOffsetX = i(-4, Integer.MIN_VALUE, Integer.MAX_VALUE, "mainMenuConfigButtonOffsetX", client,
		"Offset the Create config button in the main menu by this many pixels on the X axis",
		"The sign (+/-) of this value determines what side of the row the button appears on (right/left)");

	public static final ConfigValue<Integer> ingameMenuConfigButtonRow = i(3, 0, 5, "ingameMenuConfigButtonRow", client,
		"Choose the menu row that the Create config button appears on in the in-game menu",
		"Set to 0 to disable the button altogether");
	public static final ConfigValue<Integer> ingameMenuConfigButtonOffsetX = i(-4, Integer.MIN_VALUE, Integer.MAX_VALUE, "ingameMenuConfigButtonOffsetX", client,
		"Offset the Create config button in the in-game menu by this many pixels on the X axis",
		"The sign (+/-) of this value determines what side of the row the button appears on (right/left)");

	public static final ConfigValue<Boolean> ignoreFabulousWarning = b(false, "ignoreFabulousWarning", client,
		"Setting this to true will prevent Create from sending you a warning when playing with Fabulous graphics enabled");

	public static final CategoryBuilder placementAssist = group(1, "placementAssist", client, "Settings for the Placement Assist");
	public static final ConfigValue<PlacementIndicatorSetting> placementIndicator = e(PlacementIndicatorSetting.TEXTURE,
		"indicatorType", placementAssist,
		"What indicator should be used when showing where the assisted placement ends up relative to your crosshair",
		"Choose 'NONE' to disable the Indicator altogether");
	public static final ConfigValue<Float> indicatorScale =
		f(1.0f, 0f, "indicatorScale", placementAssist, "Change the size of the Indicator by this multiplier");

	public static final CategoryBuilder ponder = group(1, "ponder", placementAssist, "Ponder settings");
	public static final ConfigValue<Boolean> comfyReading =
		b(false, "comfyReading", ponder, "Slow down a ponder scene whenever there is text on screen.");

	@Override
	public String getName() {
		return "client";
	}

	public enum PlacementIndicatorSetting {
		TEXTURE, TRIANGLE, NONE
	}
}
