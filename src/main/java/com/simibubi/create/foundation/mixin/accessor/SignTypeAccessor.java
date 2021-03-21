package com.simibubi.create.foundation.mixin.accessor;

import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(SignType.class)
public interface SignTypeAccessor {
	@Accessor("VALUES")
	public static Set<SignType> getValues() {
		throw new AssertionError();
	}
}
