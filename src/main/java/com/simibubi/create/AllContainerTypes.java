package com.simibubi.create;

import com.simibubi.create.content.logistics.block.inventories.AdjustableCrateContainer;
import com.simibubi.create.content.logistics.block.inventories.AdjustableCrateScreen;
import com.simibubi.create.content.logistics.item.filter.AttributeFilterContainer;
import com.simibubi.create.content.logistics.item.filter.AttributeFilterScreen;
import com.simibubi.create.content.logistics.item.filter.FilterContainer;
import com.simibubi.create.content.logistics.item.filter.FilterScreen;
import com.simibubi.create.content.schematics.block.SchematicTableContainer;
import com.simibubi.create.content.schematics.block.SchematicTableScreen;
import com.simibubi.create.content.schematics.block.SchematicannonContainer;
import com.simibubi.create.content.schematics.block.SchematicannonScreen;
import com.simibubi.create.foundation.utility.Lang;

import com.simibubi.create.lib.utility.ContainerTypeFactoryWrapper;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public enum AllContainerTypes {

	SCHEMATIC_TABLE(SchematicTableContainer::new),
	SCHEMATICANNON(SchematicannonContainer::new),
	FLEXCRATE(AdjustableCrateContainer::new),
	FILTER(FilterContainer::new),
	ATTRIBUTE_FILTER(AttributeFilterContainer::new),

	;

	public ContainerType<? extends Container> type;
	private ContainerTypeFactoryWrapper<?> factory;

	<C extends Container> AllContainerTypes(ContainerTypeFactoryWrapper<C> factory) {
		this.factory = factory;
	}

	public static void register() {
		for (AllContainerTypes container : values()) {
			container.type = new ContainerType<>(container.factory);
			ScreenHandlerRegistry.registerSimple(new ResourceLocation(Create.ID, Lang.asId(container.name())), container.factory);
		}
	}

	@Environment(EnvType.CLIENT)
	public static void registerScreenFactories() {
		bind(SCHEMATIC_TABLE, SchematicTableScreen::new);
		bind(SCHEMATICANNON, SchematicannonScreen::new);
		bind(FLEXCRATE, AdjustableCrateScreen::new);
		bind(FILTER, FilterScreen::new);
		bind(ATTRIBUTE_FILTER, AttributeFilterScreen::new);
	}

	@Environment(EnvType.CLIENT)
	@SuppressWarnings("unchecked")
	private static <C extends Container, S extends Screen & IHasContainer<C>> void bind(AllContainerTypes c,
		IScreenFactory<C, S> factory) {
		ScreenManager.registerFactory((ContainerType<C>) c.type, factory);
	}

}
