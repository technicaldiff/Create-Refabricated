package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.simibubi.create.lib.extensions.BlockStateExtensions;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.entity.FlyingEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(FlyingEntity.class)
public abstract class FlyingEntityMixin {
	@ModifyVariable(at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/block/Block;getSlipperiness()F"),
			method = "Lnet/minecraft/entity/FlyingEntity;travel(Lnet/minecraft/util/math/vector/Vector3d;)V")
	public float create$setSlipperiness1(float f) {
		BlockPos create$ground = new BlockPos(
				MixinHelper.<FlyingEntity>cast(this).getX(),
				MixinHelper.<FlyingEntity>cast(this).getY() - 1.0D,
				MixinHelper.<FlyingEntity>cast(this).getZ());

		return ((BlockStateExtensions) MixinHelper.<FlyingEntity>cast(this).world.getBlockState(create$ground))
				.create$getSlipperiness(MixinHelper.<FlyingEntity>cast(this).world, create$ground, MixinHelper.<FlyingEntity>cast(this)) * 0.91F;
	}

	@ModifyVariable(at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 1, target = "Lnet/minecraft/block/Block;getSlipperiness()F"),
			method = "Lnet/minecraft/entity/FlyingEntity;travel(Lnet/minecraft/util/math/vector/Vector3d;)V")
	public float create$setSlipperiness2(float f) {
		BlockPos create$ground = new BlockPos(
				MixinHelper.<FlyingEntity>cast(this).getX(),
				MixinHelper.<FlyingEntity>cast(this).getY() - 1.0D,
				MixinHelper.<FlyingEntity>cast(this).getZ());

		return ((BlockStateExtensions) MixinHelper.<FlyingEntity>cast(this).world.getBlockState(create$ground))
				.create$getSlipperiness(MixinHelper.<FlyingEntity>cast(this).world, create$ground, MixinHelper.<FlyingEntity>cast(this)) * 0.91F;
	}
}
