package com.simibubi.create.lib.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.extensions.EntityExtensions;
import com.simibubi.create.lib.helper.EntityHelper;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityExtensions {
	@Unique
	private CompoundTag create$extraCustomData;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;writeCustomDataToTag(Lnet/minecraft/nbt/CompoundTag;)V"), method = "toTag(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;")
	public void create$beforeWriteCustomData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
		if (create$extraCustomData != null && !create$extraCustomData.isEmpty()) {
			System.out.println("writing custom data");
			tag.put(EntityHelper.EXTRA_DATA_KEY, create$extraCustomData);
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;readCustomDataFromTag(Lnet/minecraft/nbt/CompoundTag;)V"), method = "fromTag(Lnet/minecraft/nbt/CompoundTag;)V")
	public void create$beforeReadCustomData(CompoundTag tag, CallbackInfo ci) {
		if (tag.contains(EntityHelper.EXTRA_DATA_KEY)) {
			create$extraCustomData = tag.getCompound(EntityHelper.EXTRA_DATA_KEY);
		}
	}

	@Override
	public CompoundTag create$getExtraCustomData() {
		if (create$extraCustomData == null) {
			create$extraCustomData = new CompoundTag();
		}
		return create$extraCustomData;
	}
}
