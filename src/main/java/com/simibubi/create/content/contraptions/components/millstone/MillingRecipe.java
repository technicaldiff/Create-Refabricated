package com.simibubi.create.content.contraptions.components.millstone;

import javax.annotation.ParametersAreNonnullByDefault;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.lib.lba.item.RecipeWrapper;

import net.minecraft.world.World;

@ParametersAreNonnullByDefault
public class MillingRecipe extends AbstractCrushingRecipe {

	public MillingRecipe(ProcessingRecipeParams params) {
		super(AllRecipeTypes.MILLING, params);
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
			.test(inv.getStackInSlot(0));
	}

	@Override
	protected int getMaxOutputCount() {
		return 4;
	}
}
