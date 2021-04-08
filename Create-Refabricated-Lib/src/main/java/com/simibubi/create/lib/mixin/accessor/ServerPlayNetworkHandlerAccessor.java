package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.server.network.ServerPlayNetworkHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayNetworkHandler.class)
public interface ServerPlayNetworkHandlerAccessor {
	@Accessor("floatingTicks")
	int create$floatingTicks();

	@Accessor("floatingTicks")
	void create$floatingTicks(int floatingTicks);
}
