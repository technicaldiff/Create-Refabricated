package com.simibubi.create.foundation.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.util.math.Vector3f;

@Mixin(Vector3f.class)
public interface Vector3fAccessor {
	@Accessor("x")
	void setX(float x);

	@Accessor("y")
	void setY(float y);

	@Accessor("z")
	void setZ(float z);
}
