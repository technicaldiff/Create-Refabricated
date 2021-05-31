package com.simibubi.create.lib.utility;

import java.util.Arrays;

import com.simibubi.create.lib.mixin.accessor.BlockTagsAccessor;
import com.simibubi.create.lib.mixin.accessor.ItemTagsAccessor;

import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class TagUtil {
	// blocks
	public static final ITag.INamedTag<Block> WINDMILL_SAILS = BlockTagsAccessor.makeWrapperTag("windmill_sails");
	public static final ITag.INamedTag<Block> FAN_HEATERS = BlockTagsAccessor.makeWrapperTag("fan_heaters");
	public static final ITag.INamedTag<Block> WINDOWABLE = BlockTagsAccessor.makeWrapperTag("windowable");
	public static final ITag.INamedTag<Block> NON_MOVABLE = BlockTagsAccessor.makeWrapperTag("non_movable");
	public static final ITag.INamedTag<Block> BRITTLE = BlockTagsAccessor.makeWrapperTag("brittle");
	public static final ITag.INamedTag<Block> BLOCKS$SEATS = BlockTagsAccessor.makeWrapperTag("seats");
	public static final ITag.INamedTag<Block> SAILS = BlockTagsAccessor.makeWrapperTag("sails");
	public static final ITag.INamedTag<Block> BLOCKS$VALVE_HANDLES = BlockTagsAccessor.makeWrapperTag("valve_handles");
	public static final ITag.INamedTag<Block> FAN_TRANSPARENT = BlockTagsAccessor.makeWrapperTag("fan_transparent");
	public static final ITag.INamedTag<Block> SAFE_NBT = BlockTagsAccessor.makeWrapperTag("safe_nbt");

	// items
	public static final ITag.INamedTag<Item> CRUSHED_ORES = ItemTagsAccessor.makeWrapperTag("crushed_ores");
	public static final ITag.INamedTag<Item> ITEMS$SEATS = ItemTagsAccessor.makeWrapperTag("seats");
	public static final ITag.INamedTag<Item> ITEMS$VALVE_HANDLES = ItemTagsAccessor.makeWrapperTag("valve_handles");
	public static final ITag.INamedTag<Item> UPRIGHT_ON_BELT = ItemTagsAccessor.makeWrapperTag("upright_on_belt");
	public static final ITag.INamedTag<Item> CREATE_INGOTS = ItemTagsAccessor.makeWrapperTag("create_ingots");
	public static final ITag.INamedTag<Item> BEACON_PAYMENT = ItemTagsAccessor.makeWrapperTag("beacon_payment");
	public static final ITag.INamedTag<Item> INGOTS = ItemTagsAccessor.makeWrapperTag("ingots");
	public static final ITag.INamedTag<Item> NUGGETS = ItemTagsAccessor.makeWrapperTag("nuggets");
	public static final ITag.INamedTag<Item> PLATES = ItemTagsAccessor.makeWrapperTag("plates");
	public static final ITag.INamedTag<Item> COBBLESTONE = ItemTagsAccessor.makeWrapperTag("cobblestone");

	// TIC compat
	public static final ITag.INamedTag<Block> SLIMY_LOGS = BlockTagsAccessor.makeWrapperTag("slimy_logs");
	public static final ITag.INamedTag<Item> SLIMEBALLS = ItemTagsAccessor.makeWrapperTag("slime_balls");

	// dyes
	public static final ITag.INamedTag<Item> BLACK_DYES = ItemTagsAccessor.makeWrapperTag("black_dyes");
	public static final ITag.INamedTag<Item> BLUE_DYES = ItemTagsAccessor.makeWrapperTag("blue_dyes");
	public static final ITag.INamedTag<Item> BROWN_DYES = ItemTagsAccessor.makeWrapperTag("brown_dyes");
	public static final ITag.INamedTag<Item> CYAN_DYES = ItemTagsAccessor.makeWrapperTag("cyan_dyes");
	public static final ITag.INamedTag<Item> GRAY_DYES = ItemTagsAccessor.makeWrapperTag("gray_dyes");
	public static final ITag.INamedTag<Item> GREEN_DYES = ItemTagsAccessor.makeWrapperTag("green_dyes");
	public static final ITag.INamedTag<Item> LIGHT_BLUE_DYES = ItemTagsAccessor.makeWrapperTag("light_blue_dyes");
	public static final ITag.INamedTag<Item> LIGHT_GRAY_DYES = ItemTagsAccessor.makeWrapperTag("light_gray_dyes");
	public static final ITag.INamedTag<Item> LIME_DYES = ItemTagsAccessor.makeWrapperTag("lime_dyes");
	public static final ITag.INamedTag<Item> MAGENTA_DYES = ItemTagsAccessor.makeWrapperTag("magenta_dyes");
	public static final ITag.INamedTag<Item> ORANGE_DYES = ItemTagsAccessor.makeWrapperTag("orange_dyes");
	public static final ITag.INamedTag<Item> PINK_DYES = ItemTagsAccessor.makeWrapperTag("pink_dyes");
	public static final ITag.INamedTag<Item> PURPLE_DYES = ItemTagsAccessor.makeWrapperTag("purple_dyes");
	public static final ITag.INamedTag<Item> RED_DYES = ItemTagsAccessor.makeWrapperTag("red_dyes");
	public static final ITag.INamedTag<Item> WHITE_DYES = ItemTagsAccessor.makeWrapperTag("white_dyes");
	public static final ITag.INamedTag<Item> YELLOW_DYES = ItemTagsAccessor.makeWrapperTag("yellow_dyes");

	// helper methods
	public static ITag.INamedTag getTagFromResourceLocation(ResourceLocation location) {
		String name = (String) Arrays.stream(location.getPath().split("/")).toArray()[location.getPath().split("/").length - 1];
		switch (name) {
			case "windmill_sails": return WINDMILL_SAILS;
			case "fan_heaters": return FAN_HEATERS;
			case "windowable": return WINDOWABLE;
			case "non_movable": return NON_MOVABLE;
			case "brittle": return BRITTLE;
			case "seats": if (location.toString().contains("item")) {return ITEMS$SEATS;} return BLOCKS$SEATS;
			case "sails": return SAILS;
			case "valve_handles": if (location.toString().contains("item")) {return ITEMS$VALVE_HANDLES;} return BLOCKS$VALVE_HANDLES;
			case "fan_transparent": return FAN_TRANSPARENT;
			case "safe_nbt": return SAFE_NBT;
			case "crushed_ores": return CRUSHED_ORES;
			case "upright_on_belt": return UPRIGHT_ON_BELT;
			case "create_ingots": return CREATE_INGOTS;
			case "beacon_payment": return BEACON_PAYMENT;
			case "ingots": return INGOTS;
			case "nuggets": return NUGGETS;
			case "plates": return PLATES;
			case "cobblestone": return COBBLESTONE;

			case "slimy_logs": return SLIMY_LOGS;
			case "slimeballs": return SLIMEBALLS;

			case "black_dyes": return BLACK_DYES;
			case "blue_dyes": return BLUE_DYES;
			case "brown_dyes": return BROWN_DYES;
			case "cyan_dyes": return CYAN_DYES;
			case "gray_dyes": return GRAY_DYES;
			case "green_dyes": return BLUE_DYES;
			case "light_blue_dyes": return LIGHT_BLUE_DYES;
			case "light_gray_dyes": return LIGHT_GRAY_DYES;
			case "lime_dyes": return LIME_DYES;
			case "magenta_dyes": return MAGENTA_DYES;
			case "orange_dyes": return ORANGE_DYES;
			case "pink_dyes": return PINK_DYES;
			case "purple_dyes": return PURPLE_DYES;
			case "red_dyes": return RED_DYES;
			case "white_dyes": return WHITE_DYES;
			case "yellow_dyes": return YELLOW_DYES;
		}
		return null;
	}

	public static boolean isDye(Item item) {
		return  item.isIn(BLACK_DYES) ||
				item.isIn(BLUE_DYES) ||
				item.isIn(BROWN_DYES) ||
				item.isIn(CYAN_DYES) ||
				item.isIn(GRAY_DYES) ||
				item.isIn(GREEN_DYES) ||
				item.isIn(LIGHT_BLUE_DYES) ||
				item.isIn(LIGHT_GRAY_DYES) ||
				item.isIn(LIME_DYES) ||
				item.isIn(MAGENTA_DYES) ||
				item.isIn(ORANGE_DYES) ||
				item.isIn(PINK_DYES) ||
				item.isIn(PURPLE_DYES) ||
				item.isIn(RED_DYES) ||
				item.isIn(WHITE_DYES) ||
				item.isIn(YELLOW_DYES);
	}

	public static DyeColor getColorFromStack(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof DyeItem) {
			return ((DyeItem) stack.getItem()).getDyeColor();
		}

		if (item.isIn(BLACK_DYES)) return DyeColor.BLACK;
		if (item.isIn(BLUE_DYES)) return DyeColor.BLUE;
		if (item.isIn(BROWN_DYES)) return DyeColor.BROWN;
		if (item.isIn(CYAN_DYES)) return DyeColor.CYAN;
		if (item.isIn(GRAY_DYES)) return DyeColor.GRAY;
		if (item.isIn(GREEN_DYES)) return DyeColor.GREEN;
		if (item.isIn(LIGHT_BLUE_DYES)) return DyeColor.LIGHT_BLUE;
		if (item.isIn(LIGHT_GRAY_DYES)) return DyeColor.LIGHT_GRAY;
		if (item.isIn(LIME_DYES)) return DyeColor.LIME;
		if (item.isIn(MAGENTA_DYES)) return DyeColor.MAGENTA;
		if (item.isIn(ORANGE_DYES)) return DyeColor.ORANGE;
		if (item.isIn(PINK_DYES)) return DyeColor.PINK;
		if (item.isIn(PURPLE_DYES)) return DyeColor.PURPLE;
		if (item.isIn(RED_DYES)) return DyeColor.RED;
		if (item.isIn(WHITE_DYES)) return DyeColor.WHITE;
		if (item.isIn(YELLOW_DYES)) return DyeColor.YELLOW;

		// item is not in color tags, default to white I guess
		return DyeColor.WHITE;
	}
}
