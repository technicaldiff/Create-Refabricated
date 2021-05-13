package com.simibubi.create.lib.helper;

import java.util.Map;

import com.simibubi.create.lib.mixin.accessor.EntityRendererManagerAccessor;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.EntityType;

public class EntityRendererManagerHelper {
	public static PlayerRenderer getPlayerRenderer(EntityRendererManager manager) {
		return ((EntityRendererManagerAccessor) manager).getPlayerRenderer();
	}

	public static Map<EntityType<?>, EntityRenderer<?>> getRenderers(EntityRendererManager manager) {
		return ((EntityRendererManagerAccessor) manager).getRenderers();
	}
}
