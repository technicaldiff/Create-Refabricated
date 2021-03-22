package com.simibubi.create.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.KineticDebugger;
import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionHandler;
import com.simibubi.create.content.contraptions.components.structureMovement.chassis.ChassisRangeDisplay;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import com.simibubi.create.content.contraptions.components.turntable.TurntableHandler;
import com.simibubi.create.content.contraptions.goggles.GoggleOverlayRenderer;
import com.simibubi.create.content.contraptions.relays.belt.item.BeltConnectorHandler;
import com.simibubi.create.content.curiosities.symmetry.SymmetryHandler;
import com.simibubi.create.content.curiosities.tools.DeforesterItem;
import com.simibubi.create.events.custom.ClientWorldEvents;
import com.simibubi.create.events.custom.ModelsBakedCallback;
import com.simibubi.create.foundation.block.entity.behaviour.linked.LinkHandler;
import com.simibubi.create.foundation.block.entity.behaviour.linked.LinkRenderer;
import com.simibubi.create.foundation.block.entity.behaviour.scrollvalue.ScrollValueRenderer;
import com.simibubi.create.foundation.block.render.SpriteShifter;
import com.simibubi.create.foundation.gui.ScreenOpener;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.render.backend.FastRenderDispatcher;
import com.simibubi.create.foundation.render.backend.RenderWork;
import com.simibubi.create.foundation.renderState.SuperRenderTypeBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.placement.PlacementHelpers;

public class ClientEvents {
	private static final String itemPrefix = "item." + Create.ID;
	private static final String blockPrefix = "block." + Create.ID;

	public static void register() {
		ClientTickEvents.END_CLIENT_TICK.register(ClientEvents::onTick);
		ClientWorldEvents.LOAD.register(ClientEvents::onLoadWorld);
		ClientWorldEvents.UNLOAD.register(ClientEvents::onUnloadWorld);
		WorldRenderEvents.END.register(ClientEvents::onRenderWorld);
		ItemTooltipCallback.EVENT.register(ClientEvents::addToItemTooltip);
		
		ModelLoadingRegistry.INSTANCE.registerModelProvider(ClientEvents::provideExtraModels);
		ModelsBakedCallback.EVENT.register(ClientEvents::onModelsBaked);

		ClientTickEvents.END_CLIENT_TICK.register(SymmetryHandler::onClientTick);
		WorldRenderEvents.END.register(SymmetryHandler::render);
		PlayerBlockBreakEvents.AFTER.register(SymmetryHandler::onBlockDestroyed);

		UseBlockCallback.EVENT.register(LinkHandler::onBlockActivated);

		HudRenderCallback.EVENT.register(GoggleOverlayRenderer::lookingAtBlocksThroughGogglesShowsTooltip);
		PlayerBlockBreakEvents.AFTER.register(DeforesterItem::onBlockDestroyed);
		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlas, registry) -> SpriteShifter.getAllTargetSprites().forEach(registry::register));
	}

	public static void onTick(MinecraftClient client) {
		if (!isGameActive())
			return;

		World world = client.world;

		AnimationTickHolder.tick();
		FastRenderDispatcher.tick();

		ContraptionHandler.tick(world);

		ScreenOpener.tick();
		BeltConnectorHandler.tick();
		LinkRenderer.tick();
		ScrollValueRenderer.tick();
		ChassisRangeDisplay.tick();
		KineticDebugger.tick();
//		CollisionDebugger.tick();
		PlacementHelpers.tick();
		CreateClient.outliner.tickOutlines();
		CreateClient.ghostBlocks.tickGhosts();
		ContraptionRenderDispatcher.tick();
	}

	public static void onLoadWorld(MinecraftClient client, ClientWorld world) {
		CreateClient.invalidateRenderers();
		AnimationTickHolder.reset();
		world.blockEntities.forEach(CreateClient.kineticRenderer::add);
	}

	public static void onUnloadWorld(MinecraftClient client, ClientWorld world) {
		CreateClient.invalidateRenderers();
		AnimationTickHolder.reset();
	}

	public static void onRenderWorld(WorldRenderContext context) {
		TurntableHandler.gameRenderTick();
		ContraptionRenderDispatcher.renderTick();

		Vec3d cameraPos = context.camera().getPos();

		MatrixStack ms = context.matrixStack();
		ms.push();
		ms.translate(-cameraPos.getX(), -cameraPos.getY(), -cameraPos.getZ());
		SuperRenderTypeBuffer buffer = SuperRenderTypeBuffer.getInstance();

//		CouplingRenderer.renderAll(ms, buffer);
//		CreateClient.schematicHandler.render(ms, buffer);
		CreateClient.ghostBlocks.renderAll(ms, buffer);

		CreateClient.outliner.renderOutlines(ms, buffer);
//		LightVolumeDebugger.render(ms, buffer);
//		CollisionDebugger.render(ms, buffer);
		buffer.draw();
		RenderSystem.enableCull();

		ms.pop();

		RenderWork.runAll();
		FastRenderDispatcher.endFrame();
	}

	public static void addToItemTooltip(ItemStack stack, TooltipContext context, List<Text> texts) {
		if (!Create.getConfig().client.enableTooltips)
			return;
		if (MinecraftClient.getInstance().player == null)
			return;

		String translationKey = stack.getItem()
			.getTranslationKey(stack);
		if (!translationKey.startsWith(itemPrefix) && !translationKey.startsWith(blockPrefix))
			return;

		if (TooltipHelper.hasTooltip(stack, MinecraftClient.getInstance().player)) {
			List<Text> toolTip = new ArrayList<>();
			toolTip.add(texts.remove(0));
			TooltipHelper.getTooltip(stack)
				.addInformation(toolTip);
			texts.addAll(0, toolTip);
		}
	}

	protected static boolean isGameActive() {
		return !(MinecraftClient.getInstance().world == null || MinecraftClient.getInstance().player == null);
	}

	public static void provideExtraModels(ResourceManager manager, Consumer<Identifier> out) {
		AllBlockPartials.onModelRegistry(manager, out);
		CreateClient.getCustomRenderedItems().foreach((item, modelFunc) -> modelFunc.apply(null)
				.getModelLocations()
				.forEach(out::accept));
	}

	public static void onModelsBaked(BakedModelManager manager, Map<Identifier, BakedModel> models, ModelLoader loader) {
		AllBlockPartials.onModelBake(manager, models, loader);
		CreateClient.getCustomBlockModels()
			.foreach((block, modelFunc) -> swapModels(models, getAllBlockStateModelLocations(block), modelFunc));
		CreateClient.getCustomItemModels()
			.foreach((item, modelFunc) -> swapModels(models, getItemModelLocation(item), modelFunc));
		CreateClient.getCustomRenderedItems().foreach((item, modelFunc) -> {
			swapModels(models, getItemModelLocation(item), m -> modelFunc.apply(m)
				.loadPartials(loader));
		});
	}

	protected static ModelIdentifier getItemModelLocation(Item item) {
		return new ModelIdentifier(Registry.ITEM.getId(item), "inventory");
	}

	protected static List<ModelIdentifier> getAllBlockStateModelLocations(Block block) {
		List<ModelIdentifier> models = new ArrayList<>();
		block.getStateManager()
			.getStates()
			.forEach(state -> {
				models.add(getBlockModelLocation(block, BlockModels.propertyMapToString(state.getEntries())));
			});
		return models;
	}

	protected static ModelIdentifier getBlockModelLocation(Block block, String suffix) {
		return new ModelIdentifier(Registry.BLOCK.getId(block), suffix);
	}

	protected static <T extends BakedModel> void swapModels(Map<Identifier, BakedModel> modelRegistry,
		List<ModelIdentifier> locations, Function<BakedModel, T> factory) {
		locations.forEach(location -> {
			swapModels(modelRegistry, location, factory);
		});
	}
	
	protected static <T extends BakedModel> void swapModels(Map<Identifier, BakedModel> modelRegistry,
		ModelIdentifier location, Function<BakedModel, T> factory) {
		modelRegistry.put(location, factory.apply(modelRegistry.get(location)));
	}
}
