package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.BufferBuilderAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;

@Environment(EnvType.CLIENT)
public final class BufferBuilderHelper {
	public static VertexFormat getVertexFormat(BufferBuilder builder) {
		return get(builder).create$vertexFormat();
	}

	private static BufferBuilderAccessor get(BufferBuilder builder) {
		return MixinHelper.cast(builder);
	}

	private BufferBuilderHelper() {}
}
