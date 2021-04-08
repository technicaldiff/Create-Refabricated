package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Use the Fabric API version instead.
 * @see net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry
 */
@Deprecated
@Mixin(Criteria.class)
public interface CriteriaAccessor {
	@Invoker("register")
	static <T extends Criterion<?>> T create$register(T object) {
		throw new AssertionError("Invoker :)");
	}
}
