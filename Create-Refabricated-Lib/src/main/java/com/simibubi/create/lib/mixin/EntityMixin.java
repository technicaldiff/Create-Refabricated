package com.simibubi.create.lib.mixin;

import com.simibubi.create.lib.event.EntityEyeHeightCallback;

import com.simibubi.create.lib.event.StartRidingCallback;
import com.simibubi.create.lib.extensions.BlockStateExtensions;

import com.simibubi.create.lib.utility.ListenerProvider;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.extensions.EntityExtensions;
import com.simibubi.create.lib.helper.EntityHelper;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityExtensions {
	@Shadow public World world;
	@Shadow private BlockPos blockPos;
	@Unique private CompoundNBT create$extraCustomData;
	@Unique private static final Logger LOGGER = LogManager.getLogger();

	@Shadow private float eyeHeight;
	@Unique private Collection<ItemEntity> create$captureDrops = null;

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V")
	public void create$entityInit(EntityType<?> entityType, World world, CallbackInfo ci) {
		int newEyeHeight = EntityEyeHeightCallback.EVENT.invoker().onEntitySize((Entity) (Object) this);
		if (newEyeHeight != -1)
			eyeHeight = newEyeHeight;
	}

	// CAPTURE DROPS

	@Inject(locals = LocalCapture.CAPTURE_FAILHARD,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/ItemEntity;setDefaultPickupDelay()V", shift = At.Shift.AFTER),
			method = "entityDropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/item/ItemEntity;", cancellable = true)
	public void create$entityDropItem(ItemStack stack, float f, CallbackInfoReturnable<ItemEntity> cir, ItemEntity itemEntity) {
		if (create$captureDrops != null) create$captureDrops.add(itemEntity);
		else cir.cancel();
	}

	@Override
	public Collection<ItemEntity> captureDrops() {
		return create$captureDrops;
	}

	@Override
	public Collection<ItemEntity> captureDrops(Collection<ItemEntity> value) {
		Collection<ItemEntity> ret = create$captureDrops;
		create$captureDrops = value;
		return ret;
	}

	// EXTRA CUSTOM DATA

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;writeAdditional(Lnet/minecraft/nbt/CompoundNBT;)V"),
			method = "writeWithoutTypeId(Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/nbt/CompoundNBT;")
	public void create$beforeWriteCustomData(CompoundNBT tag, CallbackInfoReturnable<CompoundNBT> cir) {
		if (create$extraCustomData != null && !create$extraCustomData.isEmpty()) {
			LOGGER.debug("writing custom data");
			tag.put(EntityHelper.EXTRA_DATA_KEY, create$extraCustomData);
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;readAdditional(Lnet/minecraft/nbt/CompoundNBT;)V"), method = "read(Lnet/minecraft/nbt/CompoundNBT;)V")
	public void create$beforeReadCustomData(CompoundNBT tag, CallbackInfo ci) {
		if (tag.contains(EntityHelper.EXTRA_DATA_KEY)) {
			create$extraCustomData = tag.getCompound(EntityHelper.EXTRA_DATA_KEY);
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", shift = At.Shift.AFTER)
			, method = "spawnSprintingParticles()V", cancellable = true)
	public void create$spawnSprintingParticles(CallbackInfo ci, BlockState blockstate) {
		if (((BlockStateExtensions) blockstate).addRunningEffects(world, blockPos, MixinHelper.cast(this))) {
			ci.cancel();
		}
	}

	//

	@Inject(method = "remove()V", at = @At("HEAD"))
	public void create$remove(CallbackInfo ci) {
		if (this instanceof ListenerProvider) {
			((ListenerProvider) this).invalidate();
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;canBeRidden(Lnet/minecraft/entity/Entity;)Z", shift = At.Shift.BEFORE),
			method = "startRiding(Lnet/minecraft/entity/Entity;Z)Z", cancellable = true)
	public void startRiding(Entity entity, boolean bl, CallbackInfoReturnable<Boolean> cir) {
		if (StartRidingCallback.EVENT.invoker().onStartRiding(MixinHelper.cast(this), entity) == ActionResultType.FAIL) {
			cir.setReturnValue(false);
		}
	}

	@Override
	public CompoundNBT create$getExtraCustomData() {
		if (create$extraCustomData == null) {
			create$extraCustomData = new CompoundNBT();
		}
		return create$extraCustomData;
	}
}
