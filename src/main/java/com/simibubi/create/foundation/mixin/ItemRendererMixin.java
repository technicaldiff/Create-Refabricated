package com.simibubi.create.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.foundation.render.CustomItemRenderer;
import com.simibubi.create.foundation.render.CustomItemRendererRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/BuiltinModelItemRenderer;render(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"), method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V")
	public void onRenderItem(BuiltinModelItemRenderer renderer, ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ItemStack stack1, ModelTransformation.Mode renderMode1, boolean leftHanded1, MatrixStack matrices1, VertexConsumerProvider vertexConsumers1, int light1, int overlay1, BakedModel model) {
		CustomItemRenderer r = CustomItemRendererRegistry.getRenderer(stack.getItem());
		if (r != null) {
			r.render(stack, mode, matrices, vertexConsumers, light, overlay);
		} else {
			renderer.render(stack, mode, matrices, vertexConsumers, light, overlay);
		}
	}
}
