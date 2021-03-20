package com.simibubi.create.foundation.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.simibubi.create.foundation.utility.extensions.SignTypeExtensions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.SignType;

@Environment(EnvType.SERVER)
@Mixin(SignType.class)
public class SignTypeMixin implements SignTypeExtensions {
	@Final
	@Shadow
	private String name;

	public String getName() {
		return name;
	}
}
