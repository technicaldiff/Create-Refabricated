package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.util.DamageSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DamageSource.class)
public interface DamageSourceAccessor {

	@Invoker("setDamageBypassesArmor")
	DamageSource create$setDamageBypassesArmor();

	@Invoker("<init>")
	static DamageSource create$init(String string) {
		throw new AssertionError();
	}
}
