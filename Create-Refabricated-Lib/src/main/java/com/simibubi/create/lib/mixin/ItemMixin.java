package com.simibubi.create.lib.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.extensions.ItemExtensions;
import com.simibubi.create.lib.helper.ItemSupplier;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.item.Item;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemExtensions {
	private Supplier<Item> supplier;

	@Override
	public Supplier<Item> getSupplier() {
		return supplier;
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void item(Item.Properties properties, CallbackInfo ci) {
		supplier = new ItemSupplier(MixinHelper.cast(this));
	}
}
