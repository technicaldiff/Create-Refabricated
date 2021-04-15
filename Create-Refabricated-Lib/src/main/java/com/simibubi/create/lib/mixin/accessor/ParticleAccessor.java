package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.client.particle.Particle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface ParticleAccessor {
	@Accessor("field_21507")
	boolean create$field_21507();

	@Accessor("field_21507")
	void create$field_21507(boolean field_21507);
}
