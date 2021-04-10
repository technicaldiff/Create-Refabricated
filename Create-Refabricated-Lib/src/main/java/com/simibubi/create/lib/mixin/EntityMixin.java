package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.extensions.EntityExtensions;
import com.simibubi.create.lib.helper.EntityHelper;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityExtensions {
	@Unique
	private CompoundNBT create$extraCustomData;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;writeAdditional(Lnet/minecraft/nbt/CompoundNBT;)V"), method = "writeWithoutTypeId(Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/nbt/CompoundNBT;")
	public void create$beforeWriteCustomData(CompoundNBT tag, CallbackInfoReturnable<CompoundNBT> cir) {
		if (create$extraCustomData != null && !create$extraCustomData.isEmpty()) {
			System.out.println("writing custom data");
			tag.put(EntityHelper.EXTRA_DATA_KEY, create$extraCustomData);
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;readAdditional(Lnet/minecraft/nbt/CompoundNBT;)V"), method = "read(Lnet/minecraft/nbt/CompoundNBT;)V")
	public void create$beforeReadCustomData(CompoundNBT tag, CallbackInfo ci) {
		if (tag.contains(EntityHelper.EXTRA_DATA_KEY)) {
			create$extraCustomData = tag.getCompound(EntityHelper.EXTRA_DATA_KEY);
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
