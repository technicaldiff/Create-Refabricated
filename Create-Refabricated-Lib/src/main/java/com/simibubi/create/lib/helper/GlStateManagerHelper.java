package com.simibubi.create.lib.helper;

import com.mojang.blaze3d.platform.GlStateManager;
import com.simibubi.create.lib.mixin.accessor.GlStateManagerAccessor;

public final class GlStateManagerHelper {
	public static GlStateManager.FogState getFOG() {
		return GlStateManagerAccessor.create$FOG();
	}

	private GlStateManagerHelper() {}
}
