package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor {
	@Invoker("closeScreen")
	void create$closeScreen();
}
