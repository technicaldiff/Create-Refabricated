package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.AbstractMinecartEntityExtensions;
import com.simibubi.create.lib.mixin.accessor.AbstractMinecartEntityAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public final class AbstractMinecartEntityHelper {
	public static void moveMinecartOnRail(AbstractMinecartEntity entity, BlockPos pos) {
		((AbstractMinecartEntityExtensions) MixinHelper.cast(entity)).create$moveMinecartOnRail(pos);
	}

	public static ItemStack getCartItem(AbstractMinecartEntity entity) {
		return ((AbstractMinecartEntityExtensions) MixinHelper.cast(entity)).create$getCartItem();
	}

	public static double getMaximumSpeed(AbstractMinecartEntity entity) {
		return ((AbstractMinecartEntityAccessor) MixinHelper.cast(entity)).create$getMaximumSpeed();
	}

	private AbstractMinecartEntityHelper() {}
}
