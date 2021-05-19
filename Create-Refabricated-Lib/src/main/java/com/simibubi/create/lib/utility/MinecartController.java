package com.simibubi.create.lib.utility;

import net.minecraft.entity.item.minecart.AbstractMinecartEntity;

/**
 * Interface to allow access to MinecartController class from lib
 */
public interface MinecartController {
	MinecartController create(AbstractMinecartEntity cart);
}
