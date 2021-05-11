package com.simibubi.create.foundation.utility;

import com.simibubi.create.AllTags;
import com.simibubi.create.lib.utility.NamedTagWrapper;

import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

public class DyeHelper {

	public static IItemProvider getWoolOfDye(DyeColor color) {
		switch (color) {
		case BLACK:
			return Blocks.BLACK_WOOL;
		case BLUE:
			return Blocks.BLUE_WOOL;
		case BROWN:
			return Blocks.BROWN_WOOL;
		case CYAN:
			return Blocks.CYAN_WOOL;
		case GRAY:
			return Blocks.GRAY_WOOL;
		case GREEN:
			return Blocks.GREEN_WOOL;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_WOOL;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_WOOL;
		case LIME:
			return Blocks.LIME_WOOL;
		case MAGENTA:
			return Blocks.MAGENTA_WOOL;
		case ORANGE:
			return Blocks.ORANGE_WOOL;
		case PINK:
			return Blocks.PINK_WOOL;
		case PURPLE:
			return Blocks.PURPLE_WOOL;
		case RED:
			return Blocks.RED_WOOL;
		case YELLOW:
			return Blocks.YELLOW_WOOL;
		case WHITE:
		default:
			return Blocks.WHITE_WOOL;
		}
	}

	public static NamedTagWrapper<Item> getTagOfDye(DyeColor color) {
		switch (color) {
		case BLACK:
			return AllTags.BLACK_DYES;
		case BLUE:
			return AllTags.BLUE_DYES;
		case BROWN:
			return AllTags.BROWN_DYES;
		case CYAN:
			return AllTags.CYAN_DYES;
		case GRAY:
			return AllTags.GRAY_DYES;
		case GREEN:
			return AllTags.GREEN_DYES;
		case LIGHT_BLUE:
			return AllTags.LIGHT_BLUE_DYES;
		case LIGHT_GRAY:
			return AllTags.LIGHT_GRAY_DYES;
		case LIME:
			return AllTags.LIME_DYES;
		case MAGENTA:
			return AllTags.MAGENTA_DYES;
		case ORANGE:
			return AllTags.ORANGE_DYES;
		case PINK:
			return AllTags.PINK_DYES;
		case PURPLE:
			return AllTags.PURPLE_DYES;
		case RED:
			return AllTags.RED_DYES;
		case YELLOW:
			return AllTags.YELLOW_DYES;
		case WHITE:
		default:
			return AllTags.WHITE_DYES;
		}
	}
}
