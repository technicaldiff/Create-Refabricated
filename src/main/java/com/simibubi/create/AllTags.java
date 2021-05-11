package com.simibubi.create;

import java.util.function.Function;

import com.simibubi.create.lib.utility.NamedTagWrapper;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagRegistry;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.ResourceLocation;

public class AllTags {
//	private static final CreateRegistrate REGISTRATE = Create.registrate()
//		.itemGroup(() -> Create.baseCreativeTab);

	public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, ItemBuilder<BlockItem, BlockBuilder<T, P>>> tagBlockAndItem(
		String tagName) {
//		return b -> b.tag(forgeBlockTag(tagName))
//			.item()
//			.tag(forgeItemTag(tagName));
		return null;
	}

	public static ITag.INamedTag<Block> forgeBlockTag(String name) {
//		return forgeTag(BlockTags::makeWrapperTag, name);
		return null;
	}

	public static ITag.INamedTag<Item> forgeItemTag(String name) {
//		return forgeTag(ItemTags::makeWrapperTag, name);
		return null;
	}

	public static ITag.INamedTag<Fluid> forgeFluidTag(String name) {
//		return forgeTag(FluidTags::makeWrapperTag, name);
		return null;
	}

	public static <T> ITag.INamedTag<T> forgeTag(Function<String, ITag.INamedTag<T>> wrapperFactory, String name) {
//		return tag(wrapperFactory, "forge", name);
		return null;
	}

	public static <T> ITag.INamedTag<T> tag(Function<String, ITag.INamedTag<T>> wrapperFactory, String domain,
		String name) {
//		return wrapperFactory.apply(new ResourceLocation(domain, name).toString());
		return null;
	}

	public static enum NameSpace {

//		MOD(Create.ID), FORGE("forge"), MC("minecraft"), TIC("tconstruct")

		;

		String id;

		private NameSpace(String id) {
//			this.id = id;
		}
	}

	public static class AllItemTags {
		public static final NamedTagWrapper<Item> CRUSHED_ORES = makeWrapperTag("crushed_ores");
		public static final NamedTagWrapper<Item> SEATS = makeWrapperTag("seats");
		public static final NamedTagWrapper<Item> VALVE_HANDLES = makeWrapperTag("valve_handles");
		public static final NamedTagWrapper<Item> UPRIGHT_ON_BELT = makeWrapperTag("upright_on_belt");
		public static final NamedTagWrapper<Item> CREATE_INGOTS = makeWrapperTag("create_ingots");
		public static final NamedTagWrapper<Item> BEACON_PAYMENT = makeWrapperTag("beacon_payment");
		public static final NamedTagWrapper<Item> INGOTS = makeWrapperTag("ingots");
		public static final NamedTagWrapper<Item> NUGGETS = makeWrapperTag("nuggets");
		public static final NamedTagWrapper<Item> PLATES = makeWrapperTag("plates");
		public static final NamedTagWrapper<Item> COBBLESTONE = makeWrapperTag("cobblestone");

//		CRUSHED_ORES(MOD),
//		SEATS(MOD),
//		VALVE_HANDLES(MOD),
//		UPRIGHT_ON_BELT(MOD),
//		CREATE_INGOTS(MOD),
//		BEACON_PAYMENT(FORGE),
//		INGOTS(FORGE),
//		NUGGETS(FORGE),
//		PLATES(FORGE),
//		COBBLESTONE(FORGE)

		;

		public ITag.INamedTag<Item> tag;

		private AllItemTags(NameSpace namespace) {
//			this(namespace, "");
		}

		private AllItemTags(NameSpace namespace, String path) {
//			tag = ItemTags.makeWrapperTag(
//				new ResourceLocation(namespace.id, (path.isEmpty() ? "" : path + "/") + Lang.asId(name())).toString());
//			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> prov.getOrCreateTagBuilder(tag));
		}

		public boolean matches(ItemStack stack) {
//			return tag.contains(stack.getItem());
			return false;
		}

		public void add(Item... values) {
//			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> prov.getOrCreateTagBuilder(tag)
//				.add(values));
		}

		public void includeIn(AllItemTags parent) {
//			REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> prov.getOrCreateTagBuilder(parent.tag)
//				.addTag(tag));
		}
	}

	public static enum AllFluidTags {
//		NO_INFINITE_DRAINING

		;

		public ITag.INamedTag<Fluid> tag;

		private AllFluidTags() {
//			this(MOD, "");
		}

		private AllFluidTags(NameSpace namespace) {
//			this(namespace, "");
		}

		private AllFluidTags(NameSpace namespace, String path) {
//			tag = FluidTags.createOptional(
//				new ResourceLocation(namespace.id, (path.isEmpty() ? "" : path + "/") + Lang.asId(name())));
		}

		public boolean matches(Fluid fluid) {
//			return fluid != null && fluid.isIn(tag);
			return false;
		}

		static void loadClass() {}
	}

	public static class AllBlockTags {
		public static final NamedTagWrapper<Block> WINDMILL_SAILS = makeWrapperTag("windmill_sails");
		public static final NamedTagWrapper<Block> FAN_HEATERS = makeWrapperTag("fan_heaters");
		public static final NamedTagWrapper<Block> WINDOWABLE = makeWrapperTag("windowable");
		public static final NamedTagWrapper<Block> NON_MOVABLE = makeWrapperTag("non_movable");
		public static final NamedTagWrapper<Block> BRITTLE = makeWrapperTag("brittle");
		public static final NamedTagWrapper<Block> SEATS = makeWrapperTag("seats");
		public static final NamedTagWrapper<Block> SAILS = makeWrapperTag("sails");
		public static final NamedTagWrapper<Block> VALVE_HANDLES = makeWrapperTag("valve_handles");
		public static final NamedTagWrapper<Block> FAN_TRANSPARENT = makeWrapperTag("fan_transparent");
		public static final NamedTagWrapper<Block> SAFE_NBT = makeWrapperTag("safe_nbt");
//		public static final NamedTagWrapper<Block> SLIMY_LOGS = makeWrapperTag("slimy_logs"); // keeping this for when that TIC port is done

//		WINDMILL_SAILS,
//		FAN_HEATERS,
//		WINDOWABLE,
//		NON_MOVABLE,
//		BRITTLE,
//		SEATS,
//		SAILS,
//		VALVE_HANDLES,
//		FAN_TRANSPARENT,
//		SAFE_NBT,
//		SLIMY_LOGS(TIC)

//		;

//		public ITag.INamedTag<Block> tag;

		private AllBlockTags() {
//			this(MOD, "");
		}

		private AllBlockTags(NameSpace namespace) {
//			this(namespace, "");
		}

		private AllBlockTags(NameSpace namespace, String path) {
//			ResourceLocation id =
//				new ResourceLocation(namespace.id, (path.isEmpty() ? "" : path + "/") + Lang.asId(name()));
//			if (ModList.get()
//				.isLoaded(namespace.id)) {
//				tag = BlockTags.makeWrapperTag(id.toString());
//				REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> prov.getOrCreateTagBuilder(tag));
//			} else {
//				tag = new EmptyNamedTag<>(id);
//			}
		}

		public boolean matches(BlockState block) {
//			return tag.contains(block.getBlock());
			return false;
		}

		public void includeIn(AllBlockTags parent) {
//			REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> prov.getOrCreateTagBuilder(parent.tag)
//				.addTag(tag));
		}

		public void includeAll(ITag.INamedTag<Block> child) {
//			REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> prov.getOrCreateTagBuilder(tag)
//				.addTag(child));
		}

		public void add(Block... values) {
//			REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> prov.getOrCreateTagBuilder(tag)
//				.add(values));
		}
	}

	// fabric tags
	protected static final TagRegistry<Item> collection = TagRegistryManager.register(new ResourceLocation("items"), ITagCollectionSupplier::getItems);

	// colors
	public static final NamedTagWrapper<Item> BLACK_DYES = makeWrapperTag("black_dyes");
	public static final NamedTagWrapper<Item> BLUE_DYES = makeWrapperTag("blue_dyes");
	public static final NamedTagWrapper<Item> BROWN_DYES = makeWrapperTag("brown_dyes");
	public static final NamedTagWrapper<Item> CYAN_DYES = makeWrapperTag("cyan_dyes");
	public static final NamedTagWrapper<Item> GRAY_DYES = makeWrapperTag("gray_dyes");
	public static final NamedTagWrapper<Item> GREEN_DYES = makeWrapperTag("green_dyes");
	public static final NamedTagWrapper<Item> LIGHT_BLUE_DYES = makeWrapperTag("light_blue_dyes");
	public static final NamedTagWrapper<Item> LIGHT_GRAY_DYES = makeWrapperTag("light_gray_dyes");
	public static final NamedTagWrapper<Item> LIME_DYES = makeWrapperTag("lime_dyes");
	public static final NamedTagWrapper<Item> MAGENTA_DYES = makeWrapperTag("magenta_dyes");
	public static final NamedTagWrapper<Item> ORANGE_DYES = makeWrapperTag("orange_dyes");
	public static final NamedTagWrapper<Item> PINK_DYES = makeWrapperTag("pink_dyes");
	public static final NamedTagWrapper<Item> PURPLE_DYES = makeWrapperTag("purple_dyes");
	public static final NamedTagWrapper<Item> RED_DYES = makeWrapperTag("red_dyes");
	public static final NamedTagWrapper<Item> WHITE_DYES = makeWrapperTag("white_dyes");
	public static final NamedTagWrapper<Item> YELLOW_DYES = makeWrapperTag("yellow_dyes");

	private static NamedTagWrapper makeWrapperTag(String string) {
		return (NamedTagWrapper) collection.add(string);
	}

	public static void register() {
//		AllItemTags.CREATE_INGOTS.includeIn(AllItemTags.BEACON_PAYMENT);
//		AllItemTags.CREATE_INGOTS.includeIn(AllItemTags.INGOTS);
//
//		AllItemTags.UPRIGHT_ON_BELT.add(Items.GLASS_BOTTLE, Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION,
//			Items.HONEY_BOTTLE);
//
//		AllBlockTags.WINDMILL_SAILS.includeAll(BlockTags.WOOL);
//
//		AllBlockTags.BRITTLE.includeAll(BlockTags.DOORS);
//		AllBlockTags.BRITTLE.add(Blocks.FLOWER_POT, Blocks.BELL, Blocks.COCOA);
//
//		AllBlockTags.FAN_TRANSPARENT.includeAll(BlockTags.FENCES);
//		AllBlockTags.FAN_TRANSPARENT.add(Blocks.IRON_BARS);
//
//		AllBlockTags.FAN_HEATERS.add(Blocks.MAGMA_BLOCK, Blocks.CAMPFIRE, Blocks.LAVA, Blocks.FIRE, Blocks.SOUL_FIRE,
//			Blocks.SOUL_CAMPFIRE);
//		AllBlockTags.SAFE_NBT.includeAll(BlockTags.SIGNS);
//
//		AllFluidTags.loadClass();
	}
}
