package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.ServerPlayerEntityExtensions;

import net.minecraft.entity.player.ServerPlayerEntity;

public class FakePlayerHelper {
	public static boolean isFakePlayer(ServerPlayerEntity player) {
		return ((ServerPlayerEntityExtensions) player).create$isFakePlayer();
	}

	public static void setFake(ServerPlayerEntity player, boolean isFake) {
		((ServerPlayerEntityExtensions) player).create$setFake(isFake);
	}
}
