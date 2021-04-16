package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.block.CustomMinecartPassBehavior;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin {

	// this *should* inject into right before the 4th reference to bl, right in between the 2 if statements.
	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/item/minecart/AbstractMinecartEntity;moveAlongTrack(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", ordinal = 3),
			method = "Lnet/minecraft/entity/item/minecart/AbstractMinecartEntity;moveAlongTrack(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V")
	protected void create$moveAlongTrack(BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
		if (blockState.getBlock() instanceof CustomMinecartPassBehavior) {
			((CustomMinecartPassBehavior) blockState.getBlock()).onMinecartPass(blockState, MixinHelper.<Entity>cast(this).world, blockPos, MixinHelper.cast(this));
		}
	}
}
