package com.simibubi.create.events;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionHandler;
import com.simibubi.create.content.contraptions.wrench.WrenchItem;
import com.simibubi.create.foundation.command.AllCommands;
import com.simibubi.create.foundation.utility.WorldAttached;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CommonEvents {
	public static void register() {
		ServerWorldEvents.LOAD.register(CommonEvents::onLoadWorld);
		ServerWorldEvents.UNLOAD.register(CommonEvents::onUnloadWorld);
		ServerLifecycleEvents.SERVER_STARTED.register(CommonEvents::serverStarted);
		AttackEntityCallback.EVENT.register(CommonEvents::onEntityAttackedByPlayer);
		ServerEntityEvents.ENTITY_LOAD.register(CommonEvents::onEntityAdded);

		WorldTickCallback.EVENT.register(CommonEvents::onWorldTick);
	}

	public static void onWorldTick(World world) {
		ContraptionHandler.tick(world);
	}

	public static void onEntityAdded(Entity entity, ServerWorld world) {
		ContraptionHandler.addSpawnedContraptionsToCollisionList(entity, world);
	}

	public static void onLoadWorld(MinecraftServer server, ServerWorld world) {
		Create.redstoneLinkNetworkHandler.onLoadWorld(world);
		Create.torquePropagator.onLoadWorld(world);
	}

	public static void onUnloadWorld(MinecraftServer server, ServerWorld world) {
		Create.redstoneLinkNetworkHandler.onUnloadWorld(world);
		Create.torquePropagator.onUnloadWorld(world);
		WorldAttached.invalidateWorld(world);
	}

	public static void serverStarted(MinecraftServer minecraftServer) {
		AllCommands.register(minecraftServer.getCommandManager().getDispatcher());
	}

	public static ActionResult onEntityAttackedByPlayer(PlayerEntity playerEntity, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
		WrenchItem.wrenchInstaKillsMinecarts(playerEntity, world, hand, entity, entityHitResult);
		return ActionResult.PASS;
	}

}
