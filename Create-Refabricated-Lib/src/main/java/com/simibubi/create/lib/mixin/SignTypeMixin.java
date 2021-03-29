package com.simibubi.create.lib.mixin;

import net.minecraft.util.SignType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.simibubi.create.lib.extensions.SignTypeExtensions;

@Environment(EnvType.SERVER)
@Mixin(SignType.class)
public class SignTypeMixin implements SignTypeExtensions {
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
