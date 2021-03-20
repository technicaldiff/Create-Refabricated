package com.simibubi.create.foundation.mixin.accessor;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.SignType;

@Mixin(SignType.class)
public interface SignTypeAccessor {
	@Accessor("VALUES")
	public static Set<SignType> getValues() {
		throw new AssertionError();
	}
}
