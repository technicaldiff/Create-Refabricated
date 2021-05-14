package com.simibubi.create.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.capabilities.CapabilityProvider;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

@Mixin(Entity.class)
public abstract class HeavyBootsOnEntityMixin extends CapabilityProvider<Entity> {

	protected HeavyBootsOnEntityMixin(Class<Entity> baseClass) {
		super(baseClass);
	}

	@Shadow
	public abstract CompoundNBT getPersistentData();

	@Inject(at = @At("HEAD"), method = "canSwim", cancellable = true)
	public void noSwimmingWithHeavyBootsOn(CallbackInfoReturnable<Boolean> cir) {
		CompoundNBT persistentData = getPersistentData();
		if (persistentData.contains("HeavyBoots"))
			cir.setReturnValue(false);
	}

}
