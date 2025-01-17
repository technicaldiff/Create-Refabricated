package com.simibubi.create.foundation.data;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.item.BlockItem;

public class ModelGen {

//	public static ModelFile createOvergrown(DataGenContext<Block, ? extends Block> ctx, BlockStateProvider prov,
//		ResourceLocation block, ResourceLocation overlay) {
//		return createOvergrown(ctx, prov, block, block, block, overlay);
//	}

//	public static ModelFile createOvergrown(DataGenContext<Block, ? extends Block> ctx, BlockStateProvider prov,
//		ResourceLocation side, ResourceLocation top, ResourceLocation bottom, ResourceLocation overlay) {
//		return prov.models()
//			.withExistingParent(ctx.getName(), new ResourceLocation(Create.ID, "block/overgrown"))
//			.texture("particle", side)
//			.texture("side", side)
//			.texture("top", top)
//			.texture("bottom", bottom)
//			.texture("overlay", overlay);
//	}

	public static <I extends BlockItem, P> NonNullFunction<ItemBuilder<I, P>, P> oxidizedItemModel() {
		return b -> b
//			.model((ctx, prov) -> prov.withExistingParent(ctx.getName(),
//				prov.modLoc(AssetLookup.getOxidizedModel(ctx.getName(), 0))))
			.build();
	}

	public static <I extends BlockItem, P> NonNullFunction<ItemBuilder<I, P>, P> customItemModel() {
		return b -> b//.model(AssetLookup::customItemModel)
			.build();
	}

	public static <I extends BlockItem, P> NonNullFunction<ItemBuilder<I, P>, P> customItemModel(String... path) {
		return b -> b//.model(AssetLookup.customBlockItemModel(path))
			.build();
	}

}
