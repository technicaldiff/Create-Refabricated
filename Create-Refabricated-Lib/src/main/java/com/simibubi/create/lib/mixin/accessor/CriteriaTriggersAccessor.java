package com.simibubi.create.lib.mixin.accessor;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Use the Fabric API version instead.
 * @see net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry
 */
@Deprecated
@Mixin(CriteriaTriggers.class)
public interface CriteriaTriggersAccessor {
	@Invoker("register")
	static <T extends ICriterionTrigger<?>> T create$register(T criterion) {
		throw new AssertionError("Invoker :)");
	}
}
