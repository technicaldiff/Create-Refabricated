package com.simibubi.create.lib.mixin.accessor;

import java.util.Set;

import net.minecraft.util.SignType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SignType.class)
public interface SignTypeAccessor {
	@Accessor("VALUES")
	static Set<SignType> getValues() {
		throw new AssertionError();
	}
}
