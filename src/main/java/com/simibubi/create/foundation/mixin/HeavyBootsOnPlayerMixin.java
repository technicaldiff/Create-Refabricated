package com.simibubi.create.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.lib.helper.EntityHelper;
import com.simibubi.create.lib.utility.MixinHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class HeavyBootsOnPlayerMixin extends AbstractClientPlayerEntity {

	public HeavyBootsOnPlayerMixin(ClientWorld p_i50991_1_, GameProfile p_i50991_2_) {
		super(p_i50991_1_, p_i50991_2_);
	}

	@Inject(at = @At("HEAD"), method = "canSwim", cancellable = true)
	public void noSwimmingWithHeavyBootsOn(CallbackInfoReturnable<Boolean> cir) {
		CompoundNBT persistentData = EntityHelper.getExtraCustomData(MixinHelper.cast(this));

		if (persistentData.contains("HeavyBoots"))
			cir.setReturnValue(false);
	}

}
