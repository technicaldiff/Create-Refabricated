package com.simibubi.create.lib.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.simibubi.create.lib.extensions.FireBlockExtensions;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends AbstractFireBlock implements FireBlockExtensions {
	public FireBlockMixin(Properties properties, float f) {
		super(properties, f);
	}

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;"),
	method = "Lnet/minecraft/block/FireBlock;getStateForPlacement(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", cancellable = true)
	protected void create$customFireBehavior(IBlockReader iBlockReader, BlockPos blockPos, CallbackInfoReturnable<BlockState> cir) {
		if (canCatchFire(iBlockReader, blockPos, Direction.UP)) {
			cir.setReturnValue(getDefaultState());
		}
	}

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/state/StateHolder;with(Lnet/minecraft/state/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION,
			method = "Lnet/minecraft/block/FireBlock;getStateForPlacement(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", cancellable = true)
	protected void create$setBlockState(IBlockReader iBlockReader, BlockPos blockPos, CallbackInfoReturnable<BlockState> cir,
										BlockPos blockPos2, BlockState blockState, BlockState blockState2, // hmm yes, locals
										Direction[] var6, int var7, int var8, Direction direction, BooleanProperty booleanProperty) {
		blockState2 = blockState2.with(booleanProperty, canCatchFire(iBlockReader, blockPos.offset(direction), direction.getOpposite()));
	}

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Ljava/util/Random;nextInt(I)I"),
	locals = LocalCapture.CAPTURE_FAILEXCEPTION,
	method = "Lnet/minecraft/block/FireBlock;scheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", cancellable = true)
	public void scheduledTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random, CallbackInfo ci,
							  BlockState blockState2, boolean bl, int i) {
		if (i == 15 && random.nextInt(4) == 0 && !canCatchFire(serverWorld, blockPos.down(), Direction.UP)) {
			serverWorld.removeBlock(blockPos, false);
			ci.cancel();
		}
	}

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/block/FireBlock;canBurn(Lnet/minecraft/block/BlockState;)Z"),
	locals = LocalCapture.CAPTURE_FAILEXCEPTION,
	method = "Lnet/minecraft/block/FireBlock;areNeighborsFlammable(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true)
	private void areNeighborsFlammable(IBlockReader iBlockReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir,
									   Direction[] var3, int var4, int var5, Direction direction) {
		if (this.canCatchFire(iBlockReader, blockPos.offset(direction), direction.getOpposite())) {
			cir.setReturnValue(true);
		}
	}
}
