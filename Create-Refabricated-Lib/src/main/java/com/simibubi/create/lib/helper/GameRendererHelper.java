package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.GameRendererAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;

public final class GameRendererHelper {
	public static double getFOVModifier(GameRenderer renderer, ActiveRenderInfo camera, float tickDelta, boolean changingFov) {
		return get(renderer).create$getFOVModifier(camera, tickDelta, changingFov);
	}

	private static GameRendererAccessor get(GameRenderer renderer) {
		return MixinHelper.cast(renderer);
	}

	private GameRendererHelper() {}
}
