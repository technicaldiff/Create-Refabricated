package com.simibubi.create.lib.mixin.accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

@Environment(EnvType.CLIENT)
@Mixin(BakedQuad.class)
public interface BakedQuadAccessor {
	@Accessor("sprite")
	TextureAtlasSprite create$sprite();
}
