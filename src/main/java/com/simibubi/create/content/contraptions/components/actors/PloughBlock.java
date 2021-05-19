package com.simibubi.create.content.contraptions.components.actors;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import com.simibubi.create.lib.helper.FakePlayerHelper;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.world.server.ServerWorld;

public class PloughBlock extends AttachedActorBlock {

	public PloughBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}

	/**
	 * The OnHoeUse event takes a player, so we better not pass null
	 */
	static class PloughFakePlayer extends ServerPlayerEntity {


		public static final GameProfile PLOUGH_PROFILE =
				new GameProfile(UUID.fromString("9e2faded-eeee-4ec2-c314-dad129ae971d"), "Plough");

		public PloughFakePlayer(ServerWorld world) {
			super(world.getServer(), world, PLOUGH_PROFILE, new PlayerInteractionManager(world)); // this should work?
			FakePlayerHelper.setFake(this, true);
		}
	}
}
