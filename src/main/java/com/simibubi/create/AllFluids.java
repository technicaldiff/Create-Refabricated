package com.simibubi.create;

import javax.annotation.Nullable;

import com.simibubi.create.AllTags.AllFluidTags;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.content.contraptions.fluids.potion.PotionFluid;
import com.simibubi.create.content.palettes.AllPaletteBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import com.tterrag.registrate.util.entry.FluidEntry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;

public class AllFluids {

	private static final CreateRegistrate REGISTRATE = Create.registrate();

	public static final FluidEntry<PotionFluid> POTION =
			REGISTRATE.potionFluid("potion"/*, PotionFluidAttributes::new, PotionFluid::new*/)
					.lang(f -> "fluid.create.potion", "Potion")
					.register();

	public static final FluidEntry<VirtualFluid> TEA = REGISTRATE.virtualFluid("tea")
			.lang(f -> "fluid.create.tea", "Builder's Tea")
//			.tag(AllTags.forgeFluidTag("tea"))
			.register();

	public static final FluidEntry<VirtualFluid> MILK = REGISTRATE.virtualFluid("milk")
			.lang(f -> "fluid.create.milk", "Milk")
//			.tag(Tags.Fluids.MILK)
			.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> HONEY =
			REGISTRATE.standardFluid("honey"/*, NoColorFluidAttributes::new*/)
					.lang(f -> "fluid.create.honey", "Honey")
//					.attributes(b -> b.viscosity(500)
//							.density(1400))
					.properties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
//					.tag(AllFluidTags.HONEY.tag)
					.bucket()
					.properties(p -> (FabricItemSettings) p.maxStackSize(1))
					.build()
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> CHOCOLATE =
			REGISTRATE.standardFluid("chocolate"/*, NoColorFluidAttributes::new*/)
					.lang(f -> "fluid.create.chocolate", "Chocolate")
//					.tag(AllTags.forgeFluidTag("chocolate"))
//					.attributes(b -> b.viscosity(500)
//							.density(1400))
					.properties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.bucket()
			.properties(p -> (FabricItemSettings) p.maxStackSize(1))
			.build()
			.register();

	// Load this class

	public static void register() {}

	@Environment(EnvType.CLIENT)
	public static void assignRenderLayers() {}

	@Environment(EnvType.CLIENT)
	private static void makeTranslucent(FluidEntry<?> entry) {
//		SimpleFlowableFluid fluid = entry.get();
//		RenderTypeLookup.setRenderLayer(fluid, RenderType.getTranslucent());
//		RenderTypeLookup.setRenderLayer(fluid.getStillFluid(), RenderType.getTranslucent());

		// fabric
		BlockRenderLayerMap.INSTANCE.putFluid(entry.get(), RenderType.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putFluid(entry.get().getStillFluid(), RenderType.getTranslucent());
	}

	@Nullable
	public static BlockState getLavaInteraction(FluidState fluidState) {
		Fluid fluid = fluidState.getFluid();
		if (fluid.isEquivalentTo(HONEY.get()))
			return fluidState.isSource() ? AllPaletteBlocks.LIMESTONE.getDefaultState()
				: AllPaletteBlocks.LIMESTONE_VARIANTS.registeredBlocks.get(0)
					.getDefaultState();
		if (fluid.isEquivalentTo(CHOCOLATE.get()))
			return fluidState.isSource() ? AllPaletteBlocks.SCORIA.getDefaultState()
				: AllPaletteBlocks.SCORIA_VARIANTS.registeredBlocks.get(0)
					.getDefaultState();
		return null;
	}

	/**
	 * Removing alpha from tint prevents optifine from forcibly applying biome
	 * colors to modded fluids (Makes translucent fluids disappear)
	 */
	private static class NoColorFluidAttributes /*extends FluidAttributes*/ {
//
//		protected NoColorFluidAttributes(FluidAttributes builder, Fluid fluid) {
//			super(builder, fluid);
//			this.color(0x00ffffff);
//		}
//
//		@Override
//		public int getColor(IBlockDisplayReader world, BlockPos pos) {
//			return 0x00ffffff;
//		}
//
	}

}
