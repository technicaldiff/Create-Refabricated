package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.network.play.ServerPlayNetHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayNetHandler.class)
public interface ServerPlayNetHandlerAccessor {
	@Accessor("floatingTickCount")
	int create$floatingTickCount();

	@Accessor("floatingTickCount")
	void create$floatingTickCount(int floatingTicks);
}
