package com.simibubi.create.lib.helper;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.simibubi.create.lib.mixin.accessor.ItemRendererAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public final class ItemRendererHelper {
	public static void renderBakedItemQuads(ItemRenderer renderer, MatrixStack matricies, IVertexBuilder verticies, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
		get(renderer).create$renderBakedItemQuads(matricies, verticies, quads, stack, light, overlay);
	}

	public static TextureManager getTextureManager(ItemRenderer renderer) {
		return get(renderer).create$getTextureManager();
	}

	private static ItemRendererAccessor get(ItemRenderer renderer) {
		return MixinHelper.cast(renderer);
	}

	private ItemRendererHelper() {}
}
