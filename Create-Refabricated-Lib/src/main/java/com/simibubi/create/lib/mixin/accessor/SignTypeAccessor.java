package com.simibubi.create.lib.mixin.accessor;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.SignType;

@Mixin(SignType.class)
public interface SignTypeAccessor {
	@Accessor("VALUES")
	static Set<SignType> create$VALUES() {
		throw new AssertionError();
	}
}
