package com.simibubi.create.lib.mixin.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
	@Accessor("textureManager")
	TextureManager create$getTextureManager();

	@Invoker("renderBakedItemModel")
	void create$renderBakedItemModel(IBakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, IVertexBuilder vertices);

	@Invoker("renderBakedItemQuads")
	void create$renderBakedItemQuads(MatrixStack matricies, IVertexBuilder verticies, List<BakedQuad> quads, ItemStack stack, int light, int overlay);

	@Invoker("draw")
	void create$draw(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha);
}
