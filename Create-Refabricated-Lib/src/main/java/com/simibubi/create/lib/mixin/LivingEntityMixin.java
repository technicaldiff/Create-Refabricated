package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.event.LivingEntityEvents;
import com.simibubi.create.lib.extensions.BlockStateExtensions;
import com.simibubi.create.lib.item.EntitySwingListenerItem;
import com.simibubi.create.lib.utility.MixinHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Shadow protected PlayerEntity attackingPlayer;
	@Shadow public abstract ItemStack getHeldItem(Hand hand);

	@Environment(EnvType.CLIENT)
	@Inject(method = "swingHand(Lnet/minecraft/util/Hand;Z)V", at = @At("HEAD"), cancellable = true)
	private void create$swingHand(Hand hand, boolean bl, CallbackInfo ci) {
		ItemStack stack = getHeldItem(hand);
		if (!stack.isEmpty() && stack.getItem() instanceof EntitySwingListenerItem && ((EntitySwingListenerItem) stack.getItem())
				.onEntitySwing(stack, (LivingEntity) (Object) this)) ci.cancel();
	}

	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tick()V"))
	private void create$tick(CallbackInfo ci) {
		LivingEntityEvents.TICK.invoker().onLivingEntityTick((LivingEntity) (Object) this);
	}

	@ModifyVariable(method = "takeKnockback(FDD)V", at = @At("STORE"), ordinal = 0)
	private float create$takeKnockback(float f) {
		return LivingEntityEvents.KNOCKBACK_STRENGTH.invoker().onLivingEntityTakeKnockback(f, attackingPlayer);
	}

	@ModifyVariable(method = "dropXp()V", at = @At("STORE"), ordinal = 0)
	private int create$dropXp(int i) {
		return LivingEntityEvents.EXPERIENCE_DROP.invoker().onLivingEntityExperienceDrop(i, attackingPlayer);
	}
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/server/ServerWorld;spawnParticle(Lnet/minecraft/particles/IParticleData;DDDIDDDD)I", shift = At.Shift.BEFORE),
			method = "Lnet/minecraft/entity/LivingEntity;updateFallState(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V", cancellable = true)
	protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos, CallbackInfo ci, int i) {
		if (((BlockStateExtensions) blockState).addLandingEffects((ServerWorld) world, blockPos, blockState, MixinHelper.cast(this), i)) {
			super.updateFallState(d, bl, blockState, blockPos);
			ci.cancel();
		}
	}
}
