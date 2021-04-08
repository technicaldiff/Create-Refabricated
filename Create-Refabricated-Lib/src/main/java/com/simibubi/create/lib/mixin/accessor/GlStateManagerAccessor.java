package com.simibubi.create.lib.mixin.accessor;

import com.mojang.blaze3d.platform.GlStateManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Mixin(GlStateManager.class)
public interface GlStateManagerAccessor {
	@Accessor("FOG")
	static GlStateManager.FogState create$FOG() {
		throw new AssertionError("Mixin didn't merge, very funni");
	}
}
