package com.simibubi.create.content.contraptions.components.saw;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.ParametersAreNonnullByDefault;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.contraptions.itemAssembly.IAssemblyRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.lib.lba.item.RecipeWrapper;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.wrapper.RecipeWrapper;

@ParametersAreNonnullByDefault
public class CuttingRecipe extends ProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe {

	public CuttingRecipe(ProcessingRecipeParams params) {
		super(AllRecipeTypes.CUTTING, params);
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
			.test(inv.getStackInSlot(0));
	}

	@Override
	protected int getMaxInputCount() {
		return 1;
	}

	@Override
	protected int getMaxOutputCount() {
		return 4;
	}

	@Override
	public void addAssemblyIngredients(List<Ingredient> list) {}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ITextComponent getDescriptionForAssembly() {
		return Lang.translate("recipe.assembly.cutting");
	}

	@Override
	public void addRequiredMachines(Set<IItemProvider> list) {
		list.add(AllBlocks.MECHANICAL_SAW.get());
	}

	@Override
	public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
		return () -> SequencedAssemblySubCategory.AssemblyCutting::new;
	}

}
