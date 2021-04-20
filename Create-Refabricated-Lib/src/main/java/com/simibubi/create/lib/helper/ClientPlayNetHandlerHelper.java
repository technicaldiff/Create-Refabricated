package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.ClientPlayNetHandlerAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.client.network.play.ClientPlayNetHandler;

public final class ClientPlayNetHandlerHelper {

	public static int getViewDistance(ClientPlayNetHandler clientPlayNetHandler) {
		return get(clientPlayNetHandler).create$viewDistance();
	}

	private static ClientPlayNetHandlerAccessor get(ClientPlayNetHandler clientPlayNetHandler) {
		return MixinHelper.cast(clientPlayNetHandler);
	}

	private ClientPlayNetHandlerHelper() {}
}
