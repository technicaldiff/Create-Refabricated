// PORTED CREATE SOURCE

package com.simibubi.create.foundation.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.foundation.render.backend.gl.GlFog;

@Environment(EnvType.CLIENT)
@Mixin(GlStateManager.class)
public class GlStateManagerMixin {
	@Inject(at = @At("TAIL"), method = "fog")
	private static void copyFogColor(int pname, float[] params, CallbackInfo ci) {
		if (pname == GL11.GL_FOG_COLOR) {
			GlFog.FOG_COLOR = params;
		}
	}
}
