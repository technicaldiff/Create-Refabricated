package com.simibubi.create.lib.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;

public class GuiUtils {
	public static void drawGradientRect(Matrix4f matrix, int z, int left, int top, int right, int bottom, int startColor, int endColor) {
		float sA = (float) (startColor >> 24 & 255) / 255.0f;
		float sR = (float) (startColor >> 16 & 255) / 255.0f;
		float sG = (float) (startColor >> 8 & 255) / 255.0f;
		float sB = (float) (startColor & 255) / 255.0f;
		float eA = (float) (endColor >> 24 & 255) / 255.0f;
		float eR = (float) (endColor >> 16 & 255) / 255.0f;
		float eG = (float) (endColor >> 8 & 255) / 255.0f;
		float eB = (float) (endColor & 255) / 255.0f;

		RenderSystem.enableDepthTest();
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.shadeModel(GL11.GL_SMOOTH);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		buffer.vertex(matrix, right, top, z).color(sR, sG, sB, sA).endVertex();
		buffer.vertex(matrix, left, top, z).color(sR, sG, sB, sA).endVertex();
		buffer.vertex(matrix, left, bottom, z).color(eR, eG, eB, eA).endVertex();
		buffer.vertex(matrix, right, bottom, z).color(eR, eG, eB, eA).endVertex();
		tessellator.draw();

		RenderSystem.shadeModel(GL11.GL_FLAT);
		RenderSystem.disableBlend();
		RenderSystem.enableTexture();
	}
}
