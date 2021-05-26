package com.simibubi.create.content.contraptions.goggles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.lib.helper.ItemRendererHelper;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;

public class GogglesItemRenderer implements DynamicItemRenderer {
	@Override
	public void render(ItemStack stack, TransformType mode, MatrixStack matrices, IRenderTypeBuffer vertexConsumers, int light, int overlay) {
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		IBakedModel model;
		if (mode == TransformType.HEAD) {
			model = AllBlockPartials.GOGGLES.get();
		} else {
			model = renderer.getItemModelWithOverrides(stack, null, null);
		}
		RenderType layer = RenderTypeLookup.getItemLayer(stack, true);
		IVertexBuilder consumer = ItemRenderer.getDirectGlintVertexConsumer(vertexConsumers, layer, true, stack.hasEffect());
		ItemRendererHelper.renderBakedItemModel(renderer, model, stack, light, overlay, matrices, consumer);
	}
}
