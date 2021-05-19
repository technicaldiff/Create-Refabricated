package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.item.crafting.ShapelessRecipe;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipe$SerializerMixin {
	@ModifyConstant(method = "Lnet/minecraft/item/crafting/ShapelessRecipe$Serializer;read(Lnet/minecraft/util/ResourceLocation;Lcom/google/gson/JsonObject;)Lnet/minecraft/item/crafting/ShapelessRecipe;",
			constant = @Constant(intValue = 9))
	private static int modifyMaxItemsInRecipe() {
		return ShapedRecipeMixin.CREATE$MAX_HEIGHT * ShapedRecipeMixin.CREATE$MAX_WIDTH;
	}
}
