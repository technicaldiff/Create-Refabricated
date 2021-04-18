package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.network.play.server.SPlayerAbilitiesPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SPlayerAbilitiesPacket.class)
public interface SPlayerAbilitiesPacketAccessor {
	@Accessor("flySpeed")
	void create$flySpeed(float speed);
}
