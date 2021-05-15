package com.simibubi.create.lib.utility;

import java.util.Set;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

/**
 * This util class is different from other ones, an instance of it must be made before use.
 * See the {@link com.simibubi.create.lib.event.OnTextureStitchCallback} event for example use.
 */
@Environment(EnvType.CLIENT)
public class TextureStitchUtil {
	public AtlasTexture map;
	public Set<ResourceLocation> sprites;

	public TextureStitchUtil(AtlasTexture map, Set<ResourceLocation> locations) {
		this.map = map;
		sprites = locations;
	}

	public boolean addSprite(ResourceLocation location) {
		return sprites.add(location);
	}
}
