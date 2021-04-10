package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
	@Invoker("renderBakedItemModel")
	void create$renderBakedItemModel(IBakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, IVertexBuilder vertices);

	@Invoker("draw")
	void create$draw(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha);
}
