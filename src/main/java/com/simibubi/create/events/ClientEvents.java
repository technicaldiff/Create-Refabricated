package com.simibubi.create.events;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;

import com.simibubi.create.AllFluids;
import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.KineticDebugger;
import com.simibubi.create.content.contraptions.base.IRotate;
import com.simibubi.create.content.contraptions.components.flywheel.engine.EngineBlock;
import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionHandler;
import com.simibubi.create.content.contraptions.components.structureMovement.chassis.ChassisRangeDisplay;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import com.simibubi.create.content.contraptions.components.structureMovement.train.CouplingHandlerClient;
import com.simibubi.create.content.contraptions.components.structureMovement.train.CouplingPhysics;
import com.simibubi.create.content.contraptions.components.structureMovement.train.CouplingRenderer;
import com.simibubi.create.content.contraptions.components.structureMovement.train.capability.CapabilityMinecartController;
import com.simibubi.create.content.contraptions.components.turntable.TurntableHandler;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.relays.belt.item.BeltConnectorHandler;
import com.simibubi.create.content.curiosities.tools.ExtendoGripRenderHandler;
import com.simibubi.create.content.curiosities.zapper.ZapperItem;
import com.simibubi.create.content.curiosities.zapper.ZapperRenderHandler;
import com.simibubi.create.content.curiosities.zapper.blockzapper.BlockzapperRenderHandler;
import com.simibubi.create.content.curiosities.zapper.terrainzapper.WorldshaperRenderHandler;
import com.simibubi.create.content.logistics.block.depot.EjectorTargetHandler;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointHandler;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.networking.LeftClickPacket;
import com.simibubi.create.foundation.ponder.PonderTooltipHandler;
import com.simibubi.create.foundation.render.KineticRenderer;
import com.simibubi.create.foundation.render.backend.FastRenderDispatcher;
import com.simibubi.create.foundation.render.backend.RenderWork;
import com.simibubi.create.foundation.renderState.SuperRenderTypeBuffer;
import com.simibubi.create.foundation.tileEntity.behaviour.edgeInteraction.EdgeInteractionRenderer;
import com.simibubi.create.foundation.tileEntity.behaviour.filtering.FilteringRenderer;
import com.simibubi.create.foundation.tileEntity.behaviour.linked.LinkRenderer;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueHandler;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.ServerSpeedProvider;
import com.simibubi.create.foundation.utility.placement.PlacementHelpers;
import com.simibubi.create.foundation.utility.worldWrappers.WrappedClientWorld;
import com.simibubi.create.lib.event.ClientWorldEvents;

public class ClientEvents {

	private static final String itemPrefix = "item." + Create.ID;
	private static final String blockPrefix = "block." + Create.ID;

	public static void onTick(Minecraft client) {
		World world = client.world;

		if (!isGameActive())
			return;

		AnimationTickHolder.tick();
		FastRenderDispatcher.tick();
		ScrollValueHandler.tick();

		CreateClient.schematicSender.tick();
		CreateClient.schematicAndQuillHandler.tick();
		CreateClient.schematicHandler.tick();

		ContraptionHandler.tick(world);
		CapabilityMinecartController.tick(world);
		CouplingPhysics.tick(world);

		PonderTooltipHandler.tick();
		//ScreenOpener.tick();
		ServerSpeedProvider.clientTick();
		BeltConnectorHandler.tick();
		FilteringRenderer.tick();
		LinkRenderer.tick();
		ScrollValueRenderer.tick();
		ChassisRangeDisplay.tick();
		EdgeInteractionRenderer.tick();
		WorldshaperRenderHandler.tick();
		BlockzapperRenderHandler.tick();
		CouplingHandlerClient.tick();
		CouplingRenderer.tickDebugModeRenders();
		KineticDebugger.tick();
		ZapperRenderHandler.tick();
		ExtendoGripRenderHandler.tick();
		//		CollisionDebugger.tick();
		ArmInteractionPointHandler.tick();
		EjectorTargetHandler.tick();
		PlacementHelpers.tick();
		CreateClient.outliner.tickOutlines();
		CreateClient.ghostBlocks.tickGhosts();
		ContraptionRenderDispatcher.tick();
	}

	public static void onJoin(ClientPlayNetHandler handler, PacketSender sender, Minecraft client) {
		CreateClient.checkGraphicsFanciness();
	}

	public static void onLoadWorld(Minecraft client, ClientWorld world) {
		if (world.isRemote() && !(world instanceof WrappedClientWorld)) {
			CreateClient.invalidateRenderers(world);
			AnimationTickHolder.reset();
			KineticRenderer renderer = CreateClient.kineticRenderer.get(world);
			renderer.invalidate();
			((ClientWorld) world).loadedTileEntityList.forEach(renderer::add);
		}

		/*
		i was getting nullPointers when trying to call this during client setup,
		so i assume minecraft's language manager isn't yet fully loaded at that time.
		not sure where else to call this tho :S
		*/
		IHaveGoggleInformation.numberFormat.update();
	}

	public static void onUnloadWorld(Minecraft client, ClientWorld world) {
		if (world
			.isRemote()) {
			CreateClient.invalidateRenderers(world);
			AnimationTickHolder.reset();
		}
	}

	public static void onRenderWorld(WorldRenderContext event) {
		Vector3d cameraPos = Minecraft.getInstance().gameRenderer.getActiveRenderInfo()
			.getProjectedView();
		float pt = AnimationTickHolder.getPartialTicks();

		MatrixStack ms = event.matrixStack();
		ms.push();
		ms.translate(-cameraPos.getX(), -cameraPos.getY(), -cameraPos.getZ());
		SuperRenderTypeBuffer buffer = SuperRenderTypeBuffer.getInstance();

		CouplingRenderer.renderAll(ms, buffer);
		CreateClient.schematicHandler.render(ms, buffer);
		CreateClient.ghostBlocks.renderAll(ms, buffer);

		CreateClient.outliner.renderOutlines(ms, buffer, pt);
		//		LightVolumeDebugger.render(ms, buffer);
		buffer.draw();
		RenderSystem.enableCull();

		ms.pop();

		RenderWork.runAll();
		FastRenderDispatcher.endFrame();
	}

	public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() != ElementType.HOTBAR)
			return;

		onRenderHotbar(event.getMatrixStack(), Minecraft.getInstance()
			.getBufferBuilders()
			.getEntityVertexConsumers(), 0xF000F0, OverlayTexture.DEFAULT_UV, event.getPartialTicks());
	}

	public static void onRenderHotbar(MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay, float partialTicks) {
		CreateClient.schematicHandler.renderOverlay(ms, buffer, light, overlay, partialTicks);
	}

	public static void getItemTooltipColor(RenderTooltipEvent.Color event) {
		PonderTooltipHandler.handleTooltipColor(event);
	}

	public static void addToItemTooltip(ItemStack stack, ITooltipFlag iTooltipFlag, List<ITextComponent> itemTooltip) {
		if (!AllConfigs.CLIENT.tooltips.get())
			return;
		if (Minecraft.getInstance().player == null)
			return;

		String translationKey = stack.getItem()
			.getTranslationKey(stack);
		if (!translationKey.startsWith(itemPrefix) && !translationKey.startsWith(blockPrefix))
			return;

		if (TooltipHelper.hasTooltip(stack, Minecraft.getInstance().player)) {
			List<ITextComponent> toolTip = new ArrayList<>();
			toolTip.add(itemTooltip.remove(0));
			TooltipHelper.getTooltip(stack)
				.addInformation(toolTip);
			itemTooltip.addAll(0, toolTip);
		}
		
		if (stack.getItem() instanceof BlockItem) {
			BlockItem item = (BlockItem) stack.getItem();
			if (item.getBlock() instanceof IRotate || item.getBlock() instanceof EngineBlock) {
				List<ITextComponent> kineticStats = ItemDescription.getKineticStats(item.getBlock());
				if (!kineticStats.isEmpty()) {
					itemTooltip
						.add(new StringTextComponent(""));
					itemTooltip
						.addAll(kineticStats);
				}
			}
		}

		PonderTooltipHandler.addToTooltip(itemTooltip, stack);
	}

	public static void onRenderTick(RenderTickEvent event) {
		if (!isGameActive())
			return;
		TurntableHandler.gameRenderTick();
	}

	protected static boolean isGameActive() {
		return !(Minecraft.getInstance().world == null || Minecraft.getInstance().player == null);
	}

	public static void getFogDensity(EntityViewRenderEvent.FogDensity event) {
		ActiveRenderInfo info = event.getInfo();
		FluidState fluidState = info.getFluidState();
		if (fluidState.isEmpty())
			return;
		Fluid fluid = fluidState.getFluid();

		if (fluid.isEquivalentTo(AllFluids.CHOCOLATE.get())) {
			event.setDensity(5f);
			event.setCanceled(true);
		}

		if (fluid.isEquivalentTo(AllFluids.HONEY.get())) {
			event.setDensity(1.5f);
			event.setCanceled(true);
		}
	}

	public static void getFogColor(EntityViewRenderEvent.FogColors event) {
		ActiveRenderInfo info = event.getInfo();
		FluidState fluidState = info.getFluidState();
		if (fluidState.isEmpty())
			return;
		Fluid fluid = fluidState.getFluid();

		if (fluid.isEquivalentTo(AllFluids.CHOCOLATE.get())) {
			event.setRed(98 / 256f);
			event.setGreen(32 / 256f);
			event.setBlue(32 / 256f);
		}

		if (fluid.isEquivalentTo(AllFluids.HONEY.get())) {
			event.setRed(234 / 256f);
			event.setGreen(174 / 256f);
			event.setBlue(47 / 256f);
		}
	}

	public static void leftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof ZapperItem) {
			AllPackets.channel.sendToServer(new LeftClickPacket());
		}
	}

	public static void register() {
		ClientTickEvents.END_CLIENT_TICK.register(ClientEvents::onTick);
		ClientPlayConnectionEvents.JOIN.register(ClientEvents::onJoin);
		ClientWorldEvents.LOAD.register(ClientEvents::onLoadWorld);
		ClientWorldEvents.UNLOAD.register(ClientEvents::onUnloadWorld);
		WorldRenderEvents.END.register(ClientEvents::onRenderWorld);
		ItemTooltipCallback.EVENT.register(ClientEvents::addToItemTooltip);

		ClientChunkEvents.CHUNK_UNLOAD.register(CommonEvents::onChunkUnloaded);
		ClientTickEvents.END_WORLD_TICK.register(CommonEvents::onWorldTick);
		ClientEntityEvents.ENTITY_LOAD.register(CommonEvents::onEntityAdded);
		ClientWorldEvents.LOAD.register((client, world) -> CommonEvents.onLoadWorld(world));
		ClientWorldEvents.UNLOAD.register((client, world) -> CommonEvents.onUnloadWorld(world));
	}

}
