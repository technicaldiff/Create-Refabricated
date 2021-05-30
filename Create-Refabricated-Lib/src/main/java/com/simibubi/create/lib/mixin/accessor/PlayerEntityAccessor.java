package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor {
	@Invoker("closeScreen")
	void create$closeScreen();
}
