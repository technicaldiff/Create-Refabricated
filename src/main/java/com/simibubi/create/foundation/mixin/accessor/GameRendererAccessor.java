package com.simibubi.create.foundation.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
	@Invoker("bobView")
	void create$bobView(MatrixStack matrixStack, float f);

	@Invoker("bobViewWhenHurt")
	void create$bobViewWhenHurt(MatrixStack matrixStack, float f);

	@Invoker("getFov")
	double callGetFov(Camera camera, float tickDelta, boolean changingFov);

	@Accessor("ticks")
	int create$ticks();
}
