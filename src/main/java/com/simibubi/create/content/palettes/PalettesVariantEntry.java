package com.simibubi.create.content.palettes;

import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import me.pepperbell.reghelper.BlockRegBuilder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllColorHandlers;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.Lang;

public class PalettesVariantEntry {
	public ImmutableList<? extends Block> registeredBlocks;
	public ImmutableList<? extends Block> registeredPartials;

	public PalettesVariantEntry(PaletteStoneVariants variant, PaletteBlockPatterns[] patterns,
		Supplier<Block> initialProperties) {

		String name = Lang.asId(variant.name());
		ImmutableList.Builder<Block> registeredBlocks = ImmutableList.builder();
		ImmutableList.Builder<Block> registeredPartials = ImmutableList.builder();

		for (PaletteBlockPatterns pattern : patterns) {
			BlockRegBuilder<? extends Block> builder =
				AllPaletteBlocks.createBuilder(pattern.createName(name), pattern.getBlockFactory())
					.initialProperties(initialProperties)
					/*.blockstate(pattern.getBlockStateGenerator()
						.apply(pattern)
						.apply(name)::accept)*/;

			if (pattern.isTranslucent())
				builder.addLayer(() -> RenderLayer::getTranslucent);
			if (pattern == PaletteBlockPatterns.COBBLESTONE)
//				builder.item().tag(AllTags.AllItemTags.COBBLESTONE.tag);
			if (pattern.hasFoliage())
				builder.onRegister(AllBlocks.blockColors(() -> AllColorHandlers::getGrassyBlock));
			pattern.createCTBehaviour(variant)
				.ifPresent(b -> builder.onRegister(AllBlocks.connectedTextures(b)));

//			builder.recipe((c, p) -> {
//				p.stonecutting(DataIngredient.items(variant.getBaseBlock()
//					.get()), c::get);
//				pattern.addRecipes(variant, c, p);
//			});

			if (pattern.hasFoliage())
				builder.item()
					.build()
					.onRegisterItem(AllItems.itemColors(() -> AllColorHandlers::getGrassyItem));
			else
				builder.simpleItem();

			Block block = builder.register();
			registeredBlocks.add(block);

			for (PaletteBlockPartial<? extends Block> partialBlock : pattern.getPartials())
				registeredPartials.add(partialBlock.create(name, pattern, () -> block)
					.register());
		}

		this.registeredBlocks = registeredBlocks.build();
		this.registeredPartials = registeredPartials.build();
	}
}
