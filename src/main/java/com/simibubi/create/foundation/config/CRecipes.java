package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

public class CRecipes extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.LEVEL;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public static final CategoryBuilder recipes = group(0, "recipes", null, CServer.Comments.recipes);
	public static final ConfigValue<Boolean> bulkPressing = b(false, "bulkPressing", recipes, Comments.bulkPressing);
	public static final ConfigValue<Boolean> allowShapelessInMixer = b(true, "allowShapelessInMixer", recipes, Comments.allowShapelessInMixer);
	public static final ConfigValue<Boolean> allowShapedSquareInPress = b(true, "allowShapedSquareInPress", recipes, Comments.allowShapedSquareInPress);
	public static final ConfigValue<Boolean> allowRegularCraftingInCrafter =
		b(true, "allowRegularCraftingInCrafter", recipes, Comments.allowRegularCraftingInCrafter);
	public static final ConfigValue<Boolean> allowStonecuttingOnSaw = b(true, "allowStonecuttingOnSaw", recipes, Comments.allowStonecuttingOnSaw);
	public static final ConfigValue<Boolean> allowWoodcuttingOnSaw = b(true, "allowWoodcuttingOnSaw", recipes, Comments.allowWoodcuttingOnSaw);
	public static final ConfigValue<Integer> lightSourceCountForRefinedRadiance =
		i(10, 1, "lightSourceCountForRefinedRadiance", recipes, Comments.refinedRadiance);
	public static final ConfigValue<Boolean> enableRefinedRadianceRecipe =
		b(true, "enableRefinedRadianceRecipe", recipes, Comments.refinedRadianceRecipe);
	public static final ConfigValue<Boolean> enableShadowSteelRecipe = b(true, "enableShadowSteelRecipe", recipes, Comments.shadowSteelRecipe);

	@Override
	public String getName() {
		return "recipes";
	}

	private static class Comments {
		static String bulkPressing = "When true, allows the Mechanical Press to process entire stacks at a time.";
		static String allowShapelessInMixer =
			"When true, allows any shapeless crafting recipes to be processed by a Mechanical Mixer + Basin.";
		static String allowShapedSquareInPress =
			"When true, allows any single-ingredient 2x2 or 3x3 crafting recipes to be processed by a Mechanical Press + Basin.";
		static String allowRegularCraftingInCrafter =
			"When true, allows any standard crafting recipes to be processed by Mechanical Crafters.";
		static String allowStonecuttingOnSaw =
			"When true, allows any stonecutting recipes to be processed by a Mechanical Saw.";
		static String allowWoodcuttingOnSaw =
			"When true, allows any Druidcraft woodcutter recipes to be processed by a Mechanical Saw.";
		static String refinedRadiance =
			"The amount of Light sources destroyed before Chromatic Compound turns into Refined Radiance.";
		static String refinedRadianceRecipe = "Allow the standard in-world Refined Radiance recipes.";
		static String shadowSteelRecipe = "Allow the standard in-world Shadow Steel recipe.";
	}

}
