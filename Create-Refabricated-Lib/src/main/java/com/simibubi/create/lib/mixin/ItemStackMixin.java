package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.item.CustomMaxCountItem;
import com.simibubi.create.lib.utility.MixinHelper;
import com.simibubi.create.lib.utility.NBTSerializable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements NBTSerializable {
	@Inject(at = @At("HEAD"), method = "getMaxStackSize()I", cancellable = true)
	public void create$onGetMaxCount(CallbackInfoReturnable<Integer> cir) {
		ItemStack self = (ItemStack) (Object) this;
		Item item = self.getItem();
		if (item instanceof CustomMaxCountItem) {
			cir.setReturnValue(((CustomMaxCountItem) item).getItemStackLimit(self));
		}
	}

	@Override
	public CompoundNBT create$serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		MixinHelper.<ItemStack>cast(this).write(nbt);
		return nbt;
	}

	@Override
	public void create$deserializeNBT(CompoundNBT nbt) {
		MixinHelper.<ItemStack>cast(this).setTag(ItemStack.read(nbt).getTag());
	}
}
