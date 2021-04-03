// ORIGINAL FABRIC SOURCE
// ...kind of. This just has lots of Fabric-specific stuff in it

package com.simibubi.create;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourceManager;

import net.fabricmc.api.ClientModInitializer;

import com.simibubi.create.content.contraptions.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import com.simibubi.create.content.contraptions.relays.encased.CasingConnectivity;
import com.simibubi.create.content.schematics.ClientSchematicLoader;
import com.simibubi.create.events.ClientEvents;
import com.simibubi.create.foundation.ResourceReloadHandler;
import com.simibubi.create.foundation.block.render.CustomBlockModels;
import com.simibubi.create.foundation.item.CustomItemModels;
import com.simibubi.create.foundation.item.CustomRenderedItems;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.render.KineticRenderer;
import com.simibubi.create.foundation.render.SuperByteBufferCache;
import com.simibubi.create.foundation.render.backend.Backend;
import com.simibubi.create.foundation.utility.ghost.GhostBlocks;
import com.simibubi.create.foundation.utility.outliner.Outliner;
import com.simibubi.create.lib.event.BeforeFirstReloadCallback;

public class CreateClient implements ClientModInitializer {
	public static ClientSchematicLoader schematicSender;
	//public static SchematicHandler schematicHandler;
	//public static SchematicAndQuillHandler schematicAndQuillHandler;
	public static SuperByteBufferCache bufferCache;
	public static KineticRenderer kineticRenderer;
	public static final Outliner outliner = new Outliner();
	public static GhostBlocks ghostBlocks;

	private static CustomBlockModels customBlockModels;
	private static CustomItemModels customItemModels;
	private static CustomRenderedItems customRenderedItems;
	private static AllColorHandlers colorHandlers;
	private static CasingConnectivity casingConnectivity;

	@Override
	public void onInitializeClient() {
		BeforeFirstReloadCallback.EVENT.register(this::beforeFirstReload);
		ClientEvents.register();

		AllPackets.clientInit();

		kineticRenderer = new KineticRenderer();

		schematicSender = new ClientSchematicLoader();
		//schematicHandler = new SchematicHandler();
		//schematicAndQuillHandler = new SchematicAndQuillHandler();

		bufferCache = new SuperByteBufferCache();
		bufferCache.registerCompartment(KineticBlockEntityRenderer.KINETIC_TILE);
		bufferCache.registerCompartment(ContraptionRenderDispatcher.CONTRAPTION, 20);

		ghostBlocks = new GhostBlocks();

		AllKeys.register();
		AllEntityTypes.registerRenderers();
		getColorHandler().init();
		AllFluids.assignRenderLayers();
	}

	public void beforeFirstReload(MinecraftClient client) {
		Backend.init(client);

		ResourceManager resourceManager = client.getResourceManager();
		if (resourceManager instanceof ReloadableResourceManager)
			((ReloadableResourceManager) resourceManager).registerListener(new ResourceReloadHandler());
	}

	public static CustomItemModels getCustomItemModels() {
		if (customItemModels == null)
			customItemModels = new CustomItemModels();
		return customItemModels;
	}

	public static CustomRenderedItems getCustomRenderedItems() {
		if (customRenderedItems == null)
			customRenderedItems = new CustomRenderedItems();
		return customRenderedItems;
	}

	public static CustomBlockModels getCustomBlockModels() {
		if (customBlockModels == null)
			customBlockModels = new CustomBlockModels();
		return customBlockModels;
	}

	public static AllColorHandlers getColorHandler() {
		if (colorHandlers == null)
			colorHandlers = new AllColorHandlers();
		return colorHandlers;
	}

	public static CasingConnectivity getCasingConnectivity() {
		if (casingConnectivity == null)
			casingConnectivity = new CasingConnectivity();
		return casingConnectivity;
	}

	public static void invalidateRenderers() {
		CreateClient.bufferCache.invalidate();
		CreateClient.kineticRenderer.invalidate();
		ContraptionRenderDispatcher.invalidateAll();
	}
}
