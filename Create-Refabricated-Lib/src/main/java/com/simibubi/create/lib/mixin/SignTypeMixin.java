package com.simibubi.create.lib.mixin;

import net.minecraft.util.SignType;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
@Mixin(SignType.class)
public abstract class SignTypeMixin {
	@Final
	@Shadow
	private String name;

	public String getName() {
		return name;
	}

	public String method_24028() {
		return getName();
	}
}
