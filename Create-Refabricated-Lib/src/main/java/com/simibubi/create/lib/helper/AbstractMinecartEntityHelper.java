package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.AbstractMinecartEntityExtensions;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;

public final class AbstractMinecartEntityHelper {
	public static void moveMinecartOnRail(AbstractMinecartEntity entity, BlockPos pos) {
		get(entity).create$moveMinecartOnRail(pos);
	}

	private static AbstractMinecartEntityExtensions get(AbstractMinecartEntity entity) {
		return MixinHelper.cast(entity);
	}

	private AbstractMinecartEntityHelper() {}
}
