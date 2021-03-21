package com.simibubi.create.foundation.block.render;

import com.simibubi.create.foundation.item.PartialItemModelRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class CustomRenderedItemModelRenderer<M extends CustomRenderedItemModel> implements DynamicItemRenderer {
	@Override
	@SuppressWarnings("unchecked")
	public void render(ItemStack stack, ModelTransformation.Mode p_239207_2_, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
		M mainModel = ((M) MinecraftClient.getInstance()
			.getItemRenderer()
			.getHeldItemModel(stack, null, null));
		PartialItemModelRenderer renderer = PartialItemModelRenderer.of(stack, p_239207_2_, ms, buffer, overlay);

		ms.push();
		ms.translate(0.5F, 0.5F, 0.5F);
		render(stack, mainModel, renderer, p_239207_2_, ms, buffer, light, overlay);
		ms.pop();
	}

	protected void render(ItemStack stack, M model, PartialItemModelRenderer renderer, ModelTransformation.Mode mode, MatrixStack ms,
		VertexConsumerProvider buffer, int light, int overlay) {

	}
}
