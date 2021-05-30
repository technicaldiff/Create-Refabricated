package com.simibubi.create.lib.mixin.accessor;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.EntityType;

@Mixin(EntityRendererManager.class)
public interface EntityRendererManagerAccessor {
	@Accessor("playerRenderer")
	PlayerRenderer getPlayerRenderer();

	@Accessor("renderers")
	Map<EntityType<?>, EntityRenderer<?>> getRenderers();
}
