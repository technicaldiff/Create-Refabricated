package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.network.play.ServerPlayNetHandler;

@Mixin(ServerPlayNetHandler.class)
public interface ServerPlayNetHandlerAccessor {
	@Accessor("floatingTickCount")
	int create$floatingTickCount();

	@Accessor("floatingTickCount")
	void create$floatingTickCount(int floatingTicks);
}
