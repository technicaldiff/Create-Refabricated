package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.PlayerEntityAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.entity.player.PlayerEntity;

public class PlayerEntityHelper {
	public static void closeScreen (PlayerEntity player) {
		get(player).create$closeScreen();
	}

	private static PlayerEntityAccessor get(PlayerEntity player) {
		return MixinHelper.cast(player);
	}

	private PlayerEntityHelper() {}
}
