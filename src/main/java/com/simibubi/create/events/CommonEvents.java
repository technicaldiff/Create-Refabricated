package com.simibubi.create.events;

import com.simibubi.create.AllFluids;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionHandler;
import com.simibubi.create.content.contraptions.components.structureMovement.train.CouplingPhysics;
import com.simibubi.create.content.contraptions.components.structureMovement.train.capability.CapabilityMinecartController;
import com.simibubi.create.content.contraptions.fluids.recipe.FluidTransferRecipes;
import com.simibubi.create.content.contraptions.fluids.recipe.PotionMixingRecipeManager;
import com.simibubi.create.content.contraptions.wrench.WrenchItem;
import com.simibubi.create.content.curiosities.zapper.ZapperInteractionHandler;
import com.simibubi.create.content.curiosities.zapper.ZapperItem;
import com.simibubi.create.content.schematics.ServerSchematicLoader;
import com.simibubi.create.foundation.command.AllCommands;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.ServerSpeedProvider;
import com.simibubi.create.foundation.utility.WorldAttached;
import com.simibubi.create.foundation.utility.recipe.RecipeFinder;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

import net.fabricmc.fabric.api.event.world.WorldTickCallback;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import org.jetbrains.annotations.Nullable;

public class CommonEvents {

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(CommonEvents::onServerTick);
		ServerWorldEvents.LOAD.register(CommonEvents::onLoadWorld);
		ServerWorldEvents.UNLOAD.register(CommonEvents::onUnloadWorld);
		ServerLifecycleEvents.SERVER_STOPPED.register(CommonEvents::serverStopped);
		AttackEntityCallback.EVENT.register(CommonEvents::onEntityAttackedByPlayer);
		ServerEntityEvents.ENTITY_LOAD.register(CommonEvents::onEntityAdded);

		WorldTickCallback.EVENT.register(CommonEvents::onWorldTick);
	}

	public static void onServerTick(MinecraftServer minecraftServer) {
		if (Create.schematicReceiver == null)
			Create.schematicReceiver = new ServerSchematicLoader();
		Create.schematicReceiver.tick();
		Create.lagger.tick();
		ServerSpeedProvider.serverTick();
	}

	public static void onChunkUnloaded(ChunkEvent.Unload event) {
		CapabilityMinecartController.onChunkUnloaded(event);
	}

	public static void whenFluidsMeet(FluidPlaceBlockEvent event) {
		BlockState blockState = event.getOriginalState();
		FluidState fluidState = blockState.getFluidState();
		BlockPos pos = event.getPos();
		IWorld world = event.getWorld();

		if (fluidState.isSource() && FluidHelper.isLava(fluidState.getFluid()))
			return;

		for (Direction direction : Iterate.directions) {
			FluidState metFluidState = fluidState.isSource() ? fluidState : world.getFluidState(pos.offset(direction));
			if (!metFluidState.isTagged(FluidTags.WATER))
				continue;
			BlockState lavaInteraction = AllFluids.getLavaInteraction(metFluidState);
			if (lavaInteraction == null)
				continue;
			event.setNewState(lavaInteraction);
			break;
		}
	}

	public static void onWorldTick(World world) {
		ContraptionHandler.tick(world);
		CapabilityMinecartController.tick(world);
		CouplingPhysics.tick(world);
	}

	public static void onUpdateLivingEntity(LivingUpdateEvent event) {
		LivingEntity entityLiving = event.getEntityLiving();
		World world = entityLiving.world;
		if (world == null)
			return;
		ContraptionHandler.entitiesWhoJustDismountedGetSentToTheRightLocation(entityLiving, world);
	}

	public static void onEntityAdded(Entity entity, ServerWorld world) {
		ContraptionHandler.addSpawnedContraptionsToCollisionList(entity, world);
	}

	public static ActionResultType onEntityAttackedByPlayer(PlayerEntity playerEntity, World world, Hand hand, Entity entity, @Nullable EntityRayTraceResult entityRayTraceResult) {
		WrenchItem.wrenchInstaKillsMinecarts(playerEntity, world, hand, entity, entityRayTraceResult);
		return ActionResultType.PASS;
	}

	public static void registerCommands(RegisterCommandsEvent event) {
		AllCommands.register(event.getDispatcher());
	}

	public static void registerReloadListeners(AddReloadListenerEvent event) {
		event.addListener(RecipeFinder.LISTENER);
		event.addListener(PotionMixingRecipeManager.LISTENER);
		event.addListener(FluidTransferRecipes.LISTENER);
	}

	public static void serverStopped(MinecraftServer minecraftServer) {
		Create.schematicReceiver.shutdown();
	}

	public static void onLoadWorld(MinecraftServer minecraftServer, ServerWorld world) {
		Create.redstoneLinkNetworkHandler.onLoadWorld(world);
		Create.torquePropagator.onLoadWorld(world);
	}

	public static void onUnloadWorld(MinecraftServer minecraftServer, ServerWorld world) {
		Create.redstoneLinkNetworkHandler.onUnloadWorld(world);
		Create.torquePropagator.onUnloadWorld(world);
		WorldAttached.invalidateWorld(world);
	}

	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		CapabilityMinecartController.attach(event);
	}

	@SubscribeEvent
	public static void startTracking(PlayerEvent.StartTracking event) {
		CapabilityMinecartController.startTracking(event);
	}

	public static void leftClickEmpty(ServerPlayerEntity player) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.getItem() instanceof ZapperItem) {
			ZapperInteractionHandler.trySelect(stack, player);
		}
	}

}
