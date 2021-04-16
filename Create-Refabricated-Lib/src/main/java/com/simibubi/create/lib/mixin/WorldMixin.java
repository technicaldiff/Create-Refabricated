package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.block.CustomWeakPowerCheckingBehavior;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(World.class)
public abstract class WorldMixin {
	@Inject(at = @At("RETURN"), method = "Lnet/minecraft/world/World;getRedstonePower(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;)I", cancellable = true)
	public void create$getRedstonePower(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Integer> cir) {
		BlockState create$blockstate = MixinHelper.<World>cast(this).getBlockState(blockPos);
		int create$i = create$blockstate.getWeakPower(MixinHelper.<World>cast(this), blockPos, direction);

		if (create$blockstate.getBlock() instanceof CustomWeakPowerCheckingBehavior) {
			cir.setReturnValue(
					((CustomWeakPowerCheckingBehavior) create$blockstate.getBlock()).shouldCheckWeakPower(create$blockstate, MixinHelper.<World>cast(this), blockPos, direction)
							? Math.max(create$i, MixinHelper.<World>cast(this).getStrongPower(blockPos))
							: create$i);
		}
	}
}
