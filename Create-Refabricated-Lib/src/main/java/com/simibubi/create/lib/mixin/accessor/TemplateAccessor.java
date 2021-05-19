package com.simibubi.create.lib.mixin.accessor;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.template.Template;

@Mixin(Template.class)
public interface TemplateAccessor {
	@Invoker("loadEntity")
	static Optional<Entity> loadEntity(IServerWorld iServerWorld, CompoundNBT compoundNBT) {throw new AssertionError();}
}
