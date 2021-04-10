package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.ServerPlayNetHandlerAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.network.play.ServerPlayNetHandler;

public final class ServerPlayNetHandlerHelper {
	public static int getFloatingTickCount(ServerPlayNetHandler handler) {
		return get(handler).create$floatingTickCount();
	}

	public static void setFloatingTickCount(ServerPlayNetHandler handler, int floatingTickCount) {
		get(handler).create$floatingTickCount(floatingTickCount);
	}

	private static ServerPlayNetHandlerAccessor get(ServerPlayNetHandler handler) {
		return MixinHelper.cast(handler);
	}

	private ServerPlayNetHandlerHelper() {}
}
