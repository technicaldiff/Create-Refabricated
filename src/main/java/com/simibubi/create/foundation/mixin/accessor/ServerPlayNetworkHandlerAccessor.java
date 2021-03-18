package com.simibubi.create.foundation.mixin.accessor;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayNetworkHandler.class)
public interface ServerPlayNetworkHandlerAccessor {
	@Accessor("floatingTicks")
	public int getFloatingTicks();

	@Accessor("floatingTicks")
	public void setFloatingTicks(int floatingTicks);
}
