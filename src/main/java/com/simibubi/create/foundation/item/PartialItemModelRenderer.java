package com.simibubi.create.foundation.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import com.simibubi.create.foundation.mixin.accessor.ItemRendererAccessor;
import com.simibubi.create.foundation.renderState.RenderTypes;
import com.simibubi.create.foundation.utility.MixinHelper;

public class PartialItemModelRenderer {
	private static PartialItemModelRenderer instance;

	private ItemStack stack;
	private int overlay;
	private MatrixStack ms;
	private ModelTransformation.Mode transformType;
	private VertexConsumerProvider buffer;

	private static PartialItemModelRenderer get() {
		if (instance == null)
			instance = new PartialItemModelRenderer();
		return instance;
	}

	public static PartialItemModelRenderer of(ItemStack stack, ModelTransformation.Mode transformType, MatrixStack ms, VertexConsumerProvider buffer, int overlay) {
		PartialItemModelRenderer instance = get();
		instance.stack = stack;
		instance.buffer = buffer;
		instance.ms = ms;
		instance.transformType = transformType;
		instance.overlay = overlay;
		return instance;
	}

	public void render(BakedModel model, int light) {
		render(model, RenderTypes.getItemPartialTranslucent(), light);
	}

	public void renderSolid(BakedModel model, int light) {
		render(model, RenderTypes.getItemPartialSolid(), light);
	}

	public void renderSolidGlowing(BakedModel model, int light) {
		render(model, RenderTypes.getGlowingSolid(), light);
	}

	public void renderGlowing(BakedModel model, int light) {
		render(model, RenderTypes.getGlowingTranslucent(), light);
	}

	public void render(BakedModel model, RenderLayer type, int light) {
		if (stack.isEmpty())
			return;

		ms.push();
		ms.translate(-0.5D, -0.5D, -0.5D);

		if (!model.isBuiltin())
			renderBakedItemModel(model, light, ms,
				ItemRenderer.getArmorGlintConsumer(buffer, type, true, stack.hasGlint()));
		else
			BuiltinModelItemRenderer.INSTANCE
				.render(stack, transformType, ms, buffer, light, overlay);

		ms.pop();
	}

	private void renderBakedItemModel(BakedModel model, int light, MatrixStack ms, VertexConsumer vertices) {
		ItemRendererAccessor access = MixinHelper.cast(MinecraftClient.getInstance().getItemRenderer());
		access.create$renderBakedItemModel(model, stack, light, overlay, ms, vertices);
	}
}
