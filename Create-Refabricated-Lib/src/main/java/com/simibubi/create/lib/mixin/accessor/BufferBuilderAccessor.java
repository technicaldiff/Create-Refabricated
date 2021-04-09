package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;

@Environment(EnvType.CLIENT)
@Mixin(BufferBuilder.class)
public interface BufferBuilderAccessor {
	@Accessor("format")
	VertexFormat create$format();
}
