package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.util.DamageSource;

@Mixin(DamageSource.class)
public interface DamageSourceAccessor {
	@Invoker("<init>")
	static DamageSource create$init(String string) {
		throw new AssertionError();
	}

	@Invoker("setFireDamage")
	DamageSource create$setFireDamage();

	@Invoker("setDamageBypassesArmor")
	DamageSource create$setDamageBypassesArmor();
}
