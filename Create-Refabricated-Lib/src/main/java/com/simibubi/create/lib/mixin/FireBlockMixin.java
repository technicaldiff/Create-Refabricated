package com.simibubi.create.lib.mixin;

import java.util.Map;
import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
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
	@Shadow
	@Final
	private static Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP;
	@Unique
	IBlockReader create$reader;
	@Unique
	BlockPos create$pos;
	@Unique
	BooleanProperty create$property;
	@Unique
	Direction create$direction;

	public FireBlockMixin(Properties properties, float f) {
		super(properties, f);
	}

	@Inject(at = @At(value = "RETURN"),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION,
			method = "getStateForPlacement(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", cancellable = true)
	protected void create$getStateForPlacement(IBlockReader iBlockReader, BlockPos blockPos, CallbackInfoReturnable<BlockState> cir,
											   BlockPos blockPos2, BlockState blockState, BlockState blockState2, Direction[] var6,
											   int var7, int var8, Direction direction) {
		create$reader = iBlockReader;
		create$pos = blockPos;
		create$property = FACING_TO_PROPERTY_MAP.get(direction);
		create$direction = direction;
		if (canCatchFire(iBlockReader, blockPos, Direction.UP)) {
			cir.setReturnValue(getDefaultState());
		}
	}

	@ModifyVariable(slice = @Slice(from = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/state/StateHolder;with(Lnet/minecraft/state/Property;Ljava/lang/Comparable;)Ljava/lang/Object;")),
			at = @At(value = "STORE"),
			method = "getStateForPlacement(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;")
	protected BlockState create$setBlockState(BlockState blockState2) {
		return blockState2.with(create$property, canCatchFire(create$reader, create$pos.offset(create$direction), create$direction.getOpposite()));
	}

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 1, target = "Ljava/util/Random;nextInt(I)I"),
			method = "scheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", cancellable = true)
	public void create$scheduledTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random, CallbackInfo ci) {
		if (blockState.get(FireBlock.AGE) == 15 && random.nextInt(4) == 0 && !canCatchFire(serverWorld, blockPos.down(), Direction.UP)) {
			serverWorld.removeBlock(blockPos, false);
			ci.cancel();
		}
	}

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/block/FireBlock;canBurn(Lnet/minecraft/block/BlockState;)Z"),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION,
			method = "areNeighborsFlammable(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true)
	private void create$areNeighborsFlammable(IBlockReader iBlockReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir,
											  Direction[] var3, int var4, int var5, Direction direction) {
		if (this.canCatchFire(iBlockReader, blockPos.offset(direction), direction.getOpposite())) {
			cir.setReturnValue(true);
		}
	}
}
