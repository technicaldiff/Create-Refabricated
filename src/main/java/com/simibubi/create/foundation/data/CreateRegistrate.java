package com.simibubi.create.foundation.data;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.content.contraptions.fluids.potion.PotionFluid;
import com.simibubi.create.content.contraptions.relays.encased.CasingConnectivity;
import com.simibubi.create.foundation.block.IBlockVertexColor;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import com.simibubi.create.foundation.block.render.CustomRenderedItemModel;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.fabric.EnvExecutor;
import com.tterrag.registrate.fabric.RegistryObject;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import com.tterrag.registrate.util.NonNullLazyValue;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IItemProvider;

public class CreateRegistrate extends AbstractRegistrate<CreateRegistrate> {

	protected CreateRegistrate(String modid) {
		super(modid);
	}

	public static NonNullLazyValue<CreateRegistrate> lazy(String modid) {
		return new NonNullLazyValue<>(
			() -> new CreateRegistrate(modid)
				);
	}

	/* Section Tracking */

	private static Map<RegistryEntry<?>, AllSections> sectionLookup = new IdentityHashMap<>();
	private AllSections section;

	public CreateRegistrate startSection(AllSections section) {
		this.section = section;
		return this;
	}

	public AllSections currentSection() {
		return section;
	}

	@Override
	protected <R, T extends R> RegistryEntry<T> accept(String name,
		Class<? super R> type, Builder<R, T, ?, ?> builder, NonNullSupplier<? extends T> creator,
		NonNullFunction<RegistryObject<T>, ? extends RegistryEntry<T>> entryFactory) {
		RegistryEntry<T> ret = super.accept(name, type, builder, creator, entryFactory);
		sectionLookup.put(ret, currentSection());
		return ret;
	}

	public void addToSection(RegistryEntry<?> entry, AllSections section) {
		sectionLookup.put(entry, section);
	}

	public AllSections getSection(RegistryEntry<?> entry) {
		return sectionLookup.getOrDefault(entry, AllSections.UNASSIGNED);
	}

	public AllSections getSection(Object entry) {
		return sectionLookup.entrySet()
			.stream()
			.filter(e -> e.getKey()
				.get() == entry)
			.map(Entry::getValue)
			.findFirst()
			.orElse(AllSections.UNASSIGNED);
	}

	public <R> Collection<RegistryEntry<R>> getAll(AllSections section,
		Class<? super R> registryType) {
		return this.<R>getAll(registryType)
			.stream()
			.filter(e -> getSection(e) == section)
			.collect(Collectors.toList());
	}

	public <T extends TileEntity> CreateTileEntityBuilder<T, CreateRegistrate> tileEntity(String name,
		NonNullFunction<TileEntityType<T>, ? extends T> factory) {
		return this.tileEntity(this.self(), name, (NonNullFunction) factory);
	}

	@Override
	public <T extends TileEntity, P> CreateTileEntityBuilder<T, P> tileEntity(P parent, String name,
		NonNullFunction<TileEntityType<T>, ? extends T> factory) {
		return (CreateTileEntityBuilder<T, P>) this.entry(name, (callback) -> {
			return CreateTileEntityBuilder.create(this, parent, name, callback, factory);
		});
	}

	/* Palettes */

	public <T extends Block> BlockBuilder<T, CreateRegistrate> baseBlock(String name,
		NonNullFunction<FabricBlockSettings, T> factory, NonNullSupplier<Block> propertiesFrom, boolean TFworldGen) {
		return super.block(name, factory).initialProperties(propertiesFrom)
//			.blockstate((c, p) -> {
//				final String location = "block/palettes/" + c.getName() + "/plain";
//				p.simpleBlock(c.get(), p.models()
//					.cubeAll(c.getName(), p.modLoc(location)));
//				// TODO tag with forge:stone; if TFWorldGen == true tag with forge:wg_stone
//				// aswell
//			})
			.simpleItem();
	}

	/* Fluids */

//	public <T extends SimpleFlowableFluid> FluidBuilder<T, CreateRegistrate> virtualFluid(String name,
//		BiFunction<FluidAttributes, Fluid, FluidAttributes> attributesFactory,
//		NonNullFunction<SimpleFlowableFluid.Properties, T> factory) {
//		return entry(name,
//			c -> new VirtualFluidBuilder<>(self(), self(), name, c, Create.asResource("fluid/" + name + "_still"),
//				Create.asResource("fluid/" + name + "_flow"), attributesFactory, factory));
//	}

	public FluidBuilder<VirtualFluid, CreateRegistrate> virtualFluid(String name) {
		return entry(name,
			c -> new VirtualFluidBuilder<>(self(), self(), name, c, Create.asResource("fluid/" + name + "_still"),
				Create.asResource("fluid/" + name + "_flow"), /*null,*/ VirtualFluid::new));
	}

	// potion fluids
	public FluidBuilder<PotionFluid, CreateRegistrate> potionFluid(String name) {
		return entry(name,
				c -> new VirtualFluidBuilder<>(self(), self(), name, c, Create.asResource("fluid/" + name + "_still"),
						Create.asResource("fluid/" + name + "_flow"), /*null,*/ PotionFluid::new));
	}

	public FluidBuilder<SimpleFlowableFluid.Flowing, CreateRegistrate> standardFluid(String name) {
		return fluid(name, Create.asResource("fluid/" + name + "_still"), Create.asResource("fluid/" + name + "_flow"));
	}

//	public FluidBuilder<SimpleFlowableFluid.Flowing, CreateRegistrate> standardFluid(String name,
//		NonNullBiFunction<FluidAttributes, Fluid, FluidAttributes> attributesFactory) {
//		return fluid(name, Create.asResource("fluid/" + name + "_still"), Create.asResource("fluid/" + name + "_flow"),
//			attributesFactory);
//	}

	/* Util */

	public static <T extends Block> NonNullConsumer<? super T> connectedTextures(ConnectedTextureBehaviour behavior) {
		return entry -> onClient(() -> () -> registerCTBehviour(entry, behavior));
	}

	public static <T extends Block> NonNullConsumer<? super T> casingConnectivity(
		BiConsumer<T, CasingConnectivity> consumer) {
		return entry -> onClient(() -> () -> registerCasingConnectivity(entry, consumer));
	}

	public static <T extends Block> NonNullConsumer<? super T> blockModel(
		Supplier<NonNullFunction<IBakedModel, ? extends IBakedModel>> func) {
		return entry -> onClient(() -> () -> registerBlockModel(entry, func));
	}

	public static <T extends Block> NonNullConsumer<? super T> blockColors(Supplier<Supplier<IBlockColor>> colorFunc) {
		return entry -> onClient(() -> () -> registerBlockColor(entry, colorFunc));
	}

	public static <T extends Block> NonNullConsumer<? super T> blockVertexColors(IBlockVertexColor colorFunc) {
		return entry -> onClient(() -> () -> registerBlockVertexColor(entry, colorFunc));
	}

	public static <T extends Item> NonNullConsumer<? super T> itemModel(
		Supplier<NonNullFunction<IBakedModel, ? extends IBakedModel>> func) {
		return entry -> onClient(() -> () -> registerItemModel(entry, func));
	}

	public static <T extends Item> NonNullConsumer<? super T> itemColors(Supplier<Supplier<IItemColor>> colorFunc) {
		return entry -> onClient(() -> () -> registerItemColor(entry, colorFunc));
	}

	public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> customRenderedItem(
		Supplier<NonNullFunction<IBakedModel, ? extends CustomRenderedItemModel>> func) {
		return b -> b
			.onRegister(entry -> onClient(() -> () -> registerCustomRenderedItem(entry, func)));
	}

	protected static void onClient(Supplier<Runnable> toRun) {
		EnvExecutor.runWhenOn(EnvType.CLIENT, toRun);
	}

	@Environment(EnvType.CLIENT)
	private static void registerCTBehviour(Block entry, ConnectedTextureBehaviour behavior) {
		CreateClient.getCustomBlockModels()
			.register(() -> entry, model -> new CTModel(model, behavior));
	}

	@Environment(EnvType.CLIENT)
	private static <T extends Block> void registerCasingConnectivity(T entry,
		BiConsumer<T, CasingConnectivity> consumer) {
		consumer.accept(entry, CreateClient.getCasingConnectivity());
	}

	@Environment(EnvType.CLIENT)
	private static void registerBlockModel(Block entry,
		Supplier<NonNullFunction<IBakedModel, ? extends IBakedModel>> func) {
		CreateClient.getCustomBlockModels()
			.register(() -> entry, func.get());
	}

	@Environment(EnvType.CLIENT)
	private static void registerItemModel(Item entry,
		Supplier<NonNullFunction<IBakedModel, ? extends IBakedModel>> func) {
		CreateClient.getCustomItemModels()
			.register(() -> entry, func.get());
	}

	@Environment(EnvType.CLIENT)
	private static void registerCustomRenderedItem(Item entry,
		Supplier<NonNullFunction<IBakedModel, ? extends CustomRenderedItemModel>> func) {
		BuiltinItemRendererRegistry.INSTANCE.register(entry, func.get().apply(null).createRenderer());
		CreateClient.getCustomRenderedItems()
			.register(() -> entry, func.get());
	}

	@Environment(EnvType.CLIENT)
	private static void registerBlockColor(Block entry, Supplier<Supplier<IBlockColor>> colorFunc) {
		CreateClient.getColorHandler()
			.register(entry, colorFunc.get()
				.get());
	}

	@Environment(EnvType.CLIENT)
	private static void registerBlockVertexColor(Block entry, IBlockVertexColor colorFunc) {
		CreateClient.getColorHandler()
			.register(entry, colorFunc);
	}

	@Environment(EnvType.CLIENT)
	private static void registerItemColor(IItemProvider entry, Supplier<Supplier<IItemColor>> colorFunc) {
		CreateClient.getColorHandler()
			.register(entry, colorFunc.get()
				.get());
	}

}
