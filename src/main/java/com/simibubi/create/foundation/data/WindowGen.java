package com.simibubi.create.foundation.data;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.Create;
import com.simibubi.create.content.palettes.AllPaletteBlocks;
import com.simibubi.create.content.palettes.ConnectedGlassBlock;
import com.simibubi.create.content.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.palettes.GlassPaneBlock;
import com.simibubi.create.content.palettes.WindowBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.simibubi.create.foundation.block.connected.GlassPaneCTBehaviour;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;

public class WindowGen {
	private static Settings glassProperties(Settings p) {
		return p.allowsSpawning(WindowGen::never).solidBlock(WindowGen::never).suffocates(WindowGen::never)
				.blockVision(WindowGen::never);
	}

	private static boolean never(BlockState p_235436_0_, BlockView p_235436_1_, BlockPos p_235436_2_) {
		return false;
	}

	private static Boolean never(BlockState p_235427_0_, BlockView p_235427_1_, BlockPos p_235427_2_,
			EntityType<?> p_235427_3_) {
		return false;
	}

	public static WindowBlock woodenWindowBlock(SignType woodType, Block planksBlock) {
		return woodenWindowBlock(woodType, planksBlock, () -> RenderLayer::getCutoutMipped);
	}

	public static WindowBlock customWindowBlock(String name, Supplier<? extends ItemConvertible> ingredient,
		CTSpriteShiftEntry ct, Supplier<Supplier<RenderLayer>> renderType) {
		Function<String, Identifier> end_texture = n -> Create.id(palettesDir() + name + "_end");
		Function<String, Identifier> side_texture = n -> Create.id(palettesDir() + n);
		return windowBlock(name, ingredient, ct, renderType, end_texture, side_texture);
	}

	public static WindowBlock woodenWindowBlock(SignType woodType, Block planksBlock,
		Supplier<Supplier<RenderLayer>> renderType) {
		String woodName = woodType.getName();
		String name = woodName + "_window";
		Function<String, Identifier> end_texture =
			$ -> new Identifier("block/" + woodName + "_planks");
		Function<String, Identifier> side_texture = n -> Create.id(palettesDir() + n);
		return windowBlock(name, () -> planksBlock, AllSpriteShifts.getWoodenWindow(woodType), renderType, end_texture,
			side_texture);
	}

	public static WindowBlock windowBlock(String name, Supplier<? extends ItemConvertible> ingredient,
		CTSpriteShiftEntry ct, Supplier<Supplier<RenderLayer>> renderType,
		Function<String, Identifier> endTexture, Function<String, Identifier> sideTexture) {
		return AllPaletteBlocks.createBuilder(name, WindowBlock::new)
			.onRegister(AllBlocks.connectedTextures(new HorizontalCTBehaviour(ct)))
			.addLayer(renderType)
//			.recipe((c, p) -> ShapedRecipeJsonFactory.create(c.get(), 2)
//				.pattern(" # ")
//				.pattern("#X#")
//				.input('#', ingredient.get())
//				.input('X', DataIngredient.tag(Tags.Items.GLASS_COLORLESS))
//				.criterion("has_ingredient", p.conditionsFromItem(ingredient.get()))
//				.offerTo(p::accept))
			.initialProperties(() -> Blocks.GLASS)
			.properties(WindowGen::glassProperties)
//			.loot((t, g) -> t.addDropWithSilkTouch(g))
//			.blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
//				.cubeColumn(c.getName(), sideTexture.apply(c.getName()), endTexture.apply(c.getName()))))
//			.tag(BlockTags.IMPERMEABLE)
			.simpleItem()
			.register();
	}

	public static ConnectedGlassBlock framedGlass(String name, ConnectedTextureBehaviour behaviour) {
		return AllPaletteBlocks.createBuilder(name, ConnectedGlassBlock::new)
			.onRegister(AllBlocks.connectedTextures(behaviour))
			.addLayer(() -> RenderLayer::getTranslucent)
			.initialProperties(() -> Blocks.GLASS)
			.properties(WindowGen::glassProperties)
//			.loot((t, g) -> t.addDropWithSilkTouch(g))
//			.recipe((c, p) -> p.stonecutting(DataIngredient.tag(Tags.Items.GLASS_COLORLESS), c::get))
//			.blockstate((c, p) -> BlockStateGen.cubeAll(c, p, "palettes/", "framed_glass"))
//			.tag(Tags.Blocks.GLASS_COLORLESS, BlockTags.IMPERMEABLE)
			.item()
//			.tag(Tags.Items.GLASS_COLORLESS)
//			.model((c, p) -> p.cubeColumn(c.getName(), p.modLoc(palettesDir() + c.getName()),
//				p.modLoc("block/palettes/framed_glass")))
			.build()
			.register();
	}

	public static ConnectedGlassPaneBlock framedGlassPane(String name, Supplier<? extends Block> parent,
		CTSpriteShiftEntry ctshift) {
		Identifier sideTexture = Create.id(palettesDir() + "framed_glass");
		Identifier itemSideTexture = Create.id(palettesDir() + name);
		Identifier topTexture = Create.id(palettesDir() + "framed_glass_pane_top");
		Supplier<Supplier<RenderLayer>> renderType = () -> RenderLayer::getTranslucent;
		return connectedGlassPane(name, parent, ctshift, sideTexture, itemSideTexture, topTexture, renderType);
	}

	public static ConnectedGlassPaneBlock customWindowPane(String name, Supplier<? extends Block> parent,
		CTSpriteShiftEntry ctshift, Supplier<Supplier<RenderLayer>> renderType) {
		Identifier topTexture = Create.id(palettesDir() + name + "_pane_top");
		Identifier sideTexture = Create.id(palettesDir() + name);
		return connectedGlassPane(name, parent, ctshift, sideTexture, sideTexture, topTexture, renderType);
	}

	public static ConnectedGlassPaneBlock woodenWindowPane(SignType woodType,
		Supplier<? extends Block> parent) {
		return woodenWindowPane(woodType, parent, () -> RenderLayer::getCutoutMipped);
	}

	public static ConnectedGlassPaneBlock woodenWindowPane(SignType woodType,
		Supplier<? extends Block> parent, Supplier<Supplier<RenderLayer>> renderType) {
		String woodName = woodType.getName();
		String name = woodName + "_window";
		Identifier topTexture = new Identifier("block/" + woodName + "_planks");
		Identifier sideTexture = Create.id(palettesDir() + name);
		return connectedGlassPane(name, parent, AllSpriteShifts.getWoodenWindow(woodType), sideTexture, sideTexture,
			topTexture, renderType);
	}

	public static GlassPaneBlock standardGlassPane(String name, Supplier<? extends Block> parent,
		Identifier sideTexture, Identifier topTexture, Supplier<Supplier<RenderLayer>> renderType) {
//		BiConsumer<DataGenContext<Block, GlassPaneBlock>, RegistrateBlockstateProvider> stateProvider =
//			(c, p) -> p.paneBlock(c.get(), sideTexture, topTexture);
		return glassPane(name, parent, sideTexture, topTexture, GlassPaneBlock::new, renderType, $ -> {
		}/*, stateProvider*/);
	}

	private static ConnectedGlassPaneBlock connectedGlassPane(String name, Supplier<? extends Block> parent,
		CTSpriteShiftEntry ctshift, Identifier sideTexture, Identifier itemSideTexture,
		Identifier topTexture, Supplier<Supplier<RenderLayer>> renderType) {
		Consumer<? super ConnectedGlassPaneBlock> connectedTextures =
			AllBlocks.connectedTextures(new GlassPaneCTBehaviour(ctshift));
//		String CGPparents = "block/connected_glass_pane/";
//		String prefix = name + "_pane_";

//		Function<RegistrateBlockstateProvider, ModelFile> post =
//			getPaneModelProvider(CGPparents, prefix, "post", sideTexture, topTexture),
//			side = getPaneModelProvider(CGPparents, prefix, "side", sideTexture, topTexture),
//			sideAlt = getPaneModelProvider(CGPparents, prefix, "side_alt", sideTexture, topTexture),
//			noSide = getPaneModelProvider(CGPparents, prefix, "noside", sideTexture, topTexture),
//			noSideAlt = getPaneModelProvider(CGPparents, prefix, "noside_alt", sideTexture, topTexture);

//		BiConsumer<DataGenContext<Block, ConnectedGlassPaneBlock>, RegistrateBlockstateProvider> stateProvider =
//			(c, p) -> p.paneBlock(c.get(), post.apply(p), side.apply(p), sideAlt.apply(p), noSide.apply(p),
//				noSideAlt.apply(p));

		return glassPane(name, parent, itemSideTexture, topTexture, ConnectedGlassPaneBlock::new, renderType,
			connectedTextures/*, stateProvider*/);
	}

//	private static Function<RegistrateBlockstateProvider, ModelFile> getPaneModelProvider(String CGPparents,
//		String prefix, String partial, Identifier sideTexture, Identifier topTexture) {
//		return p -> p.models()
//			.withExistingParent(prefix + partial, Create.asResource(CGPparents + partial))
//			.texture("pane", sideTexture)
//			.texture("edge", topTexture);
//	}

	private static <G extends GlassPaneBlock> G glassPane(String name, Supplier<? extends Block> parent,
		Identifier sideTexture, Identifier topTexture, Function<FabricBlockSettings, G> factory,
		Supplier<Supplier<RenderLayer>> renderType, Consumer<? super G> connectedTextures/*,
		BiConsumer<DataGenContext<Block, G>, RegistrateBlockstateProvider> stateProvider*/) {
		name += "_pane";

		return AllPaletteBlocks.createBuilder(name, factory)
			.onRegister(connectedTextures)
			.addLayer(renderType)
			.initialProperties(() -> Blocks.GLASS_PANE)
//			.blockstate(stateProvider)
//			.recipe((c, p) -> ShapedRecipeJsonFactory.create(c.get(), 16)
//				.pattern("###")
//				.pattern("###")
//				.input('#', parent.get())
//				.criterion("has_ingredient", p.conditionsFromItem(parent.get()))
//				.offerTo(p::accept))
//			.tag(Tags.Blocks.GLASS_PANES)
//			.loot((t, g) -> t.addDropWithSilkTouch(g))
			.item()
//			.tag(Tags.Items.GLASS_PANES)
//			.model((c, p) -> p.withExistingParent(c.getName(), new Identifier(Create.ID, "item/pane"))
//				.texture("pane", sideTexture)
//				.texture("edge", topTexture))
			.build()
			.register();
	}

	private static String palettesDir() {
		return "block/palettes/";
	}
}
