package com.simibubi.create.lib.mixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.gson.JsonArray;
import com.simibubi.create.lib.extensions.ShapedRecipeExtensions;

import net.minecraft.item.crafting.ShapedRecipe;

@Mixin(ShapedRecipe.class)
public abstract class ShapedRecipeMixin implements ShapedRecipeExtensions {
	private static final Logger LOGGER = LogManager.getLogger();
	static int MAX_WIDTH = 3;
	static int MAX_HEIGHT = 3;

	@Override
	public void setCraftingSize(int width, int height) {
		if (MAX_WIDTH < width) MAX_WIDTH = width;
		if (MAX_HEIGHT < height) MAX_HEIGHT = height;
	}

	@ModifyConstant(method = "Lnet/minecraft/item/crafting/ShapedRecipe;patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
	constant = @Constant(intValue = 3, ordinal = 0))
	private static int modifyMaxHeight() {
		return MAX_HEIGHT;
	}

	@ModifyConstant(method = "Lnet/minecraft/item/crafting/ShapedRecipe;patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
			constant = @Constant(intValue = 3, ordinal = 1))
	private static int modifyMaxWidth() {
		return MAX_WIDTH;
	}

	private static void warn() {
		LOGGER.warn("The following error may be inaccurate, there is no check for the actual maximum size of a recipe.");
	}

	// these should inject into before each thrown JsonSyntaxException
	// 🍝

	@Inject(method = "Lnet/minecraft/item/crafting/ShapedRecipe;patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
	at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonSyntaxException;<init>(Ljava/lang/String;)V", ordinal = 0))
	private static void changeWidthAndHeightWarning1(JsonArray jsonArray, CallbackInfoReturnable<String[]> cir) {
		warn();
	}

	@Inject(method = "Lnet/minecraft/item/crafting/ShapedRecipe;patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
			at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonSyntaxException;<init>(Ljava/lang/String;)V", ordinal = 1))
	private static void changeWidthAndHeightWarning2(JsonArray jsonArray, CallbackInfoReturnable<String[]> cir) {
		warn();
	}

	@Inject(method = "Lnet/minecraft/item/crafting/ShapedRecipe;patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
			at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonSyntaxException;<init>(Ljava/lang/String;)V", ordinal = 2))
	private static void changeWidthAndHeightWarning3(JsonArray jsonArray, CallbackInfoReturnable<String[]> cir) {
		warn();
	}

	@Inject(method = "Lnet/minecraft/item/crafting/ShapedRecipe;patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
			at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonSyntaxException;<init>(Ljava/lang/String;)V", ordinal = 3))
	private static void changeWidthAndHeightWarning4(JsonArray jsonArray, CallbackInfoReturnable<String[]> cir) {
		warn();
	}
}
