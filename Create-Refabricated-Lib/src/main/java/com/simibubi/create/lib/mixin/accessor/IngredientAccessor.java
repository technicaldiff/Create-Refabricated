package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.crafting.Ingredient;

@Mixin(Ingredient.class)
public interface IngredientAccessor {
	@Accessor("acceptedItems")
	Ingredient.IItemList[] getAcceptedItems();
}
