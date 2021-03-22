package com.simibubi.create.foundation.mixin.accessor;

import java.util.Set;

import net.minecraft.util.SignType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SignType.class)
public interface SignTypeAccessor {
	@Accessor("VALUES")
	public static Set<SignType> getValues() {
		throw new AssertionError();
	}
}
