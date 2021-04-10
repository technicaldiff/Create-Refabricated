package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
	@Accessor("rendererUpdateCount")
	int create$rendererUpdateCount();

	@Invoker("bobView")
	void create$bobView(MatrixStack matrixStack, float f);

	@Invoker("bobViewWhenHurt")
	void create$bobViewWhenHurt(MatrixStack matrixStack, float f);

	@Invoker("getFOVModifier")
	double create$getFOVModifier(ActiveRenderInfo camera, float tickDelta, boolean changingFov);
}
