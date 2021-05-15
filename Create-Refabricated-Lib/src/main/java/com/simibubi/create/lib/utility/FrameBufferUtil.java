package com.simibubi.create.lib.utility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class FrameBufferUtil {
	public static void enableStencil(Framebuffer buffer) {
		buffer.func_216491_a(buffer.framebufferWidth, buffer.framebufferHeight, Minecraft.IS_RUNNING_ON_MAC);
	}
}
