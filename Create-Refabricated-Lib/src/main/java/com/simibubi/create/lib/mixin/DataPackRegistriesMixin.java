package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.event.DataPackReloadCallback;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.command.Commands;
import net.minecraft.resources.DataPackRegistries;

@Mixin(DataPackRegistries.class)
public abstract class DataPackRegistriesMixin {
	@Inject(at = @At("TAIL"),
			method = "<init>(Lnet/minecraft/command/Commands$EnvironmentType;I)V")
	public void create$DataPackRegistries(Commands.EnvironmentType environmentType, int i, CallbackInfo ci) {
		DataPackReloadCallback.EVENT.invoker().onDataPackReload(MixinHelper.cast(this));
	}
}
