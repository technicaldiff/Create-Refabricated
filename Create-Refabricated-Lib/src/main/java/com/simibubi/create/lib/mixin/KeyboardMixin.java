package com.simibubi.create.lib.mixin;

import net.minecraft.client.Keyboard;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.event.KeyInputCallback;

@Environment(EnvType.CLIENT)
@Mixin(Keyboard.class)
public class KeyboardMixin {
	// First return opcode is jumped over if condition is met.
	@Inject(slice = @Slice(from = @At(value = "RETURN", ordinal = 0, shift = Shift.AFTER)), at = @At(value = "RETURN"), method = "onKey(JIIII)V")
	public void onHandleKeyInput(long window, int key, int scancode, int action, int mods, CallbackInfo ci) {
		KeyInputCallback.EVENT.invoker().onKeyInput(key, scancode, action, mods);
	}
}
