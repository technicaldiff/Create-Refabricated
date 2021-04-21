package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.SPlayerAbilitiesPacketAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.network.play.server.SPlayerAbilitiesPacket;

public final class SPlayerAbilitiesPacketHelper {
	public static void setFlySpeed(SPlayerAbilitiesPacket sPlayerAbilitiesPacket, float speed) {
		get(sPlayerAbilitiesPacket).create$flySpeed(speed);
	}

	private static SPlayerAbilitiesPacketAccessor get(SPlayerAbilitiesPacket sPlayerAbilitiesPacket) {
		return MixinHelper.cast(sPlayerAbilitiesPacket);
	}

	private SPlayerAbilitiesPacketHelper() {}
}
