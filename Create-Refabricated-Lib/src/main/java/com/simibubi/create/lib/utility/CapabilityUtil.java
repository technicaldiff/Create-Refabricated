package com.simibubi.create.lib.utility;

import com.simibubi.create.lib.capabilities.Capability;
import com.simibubi.create.lib.capabilities.CapabilityProvider;

import net.minecraft.util.Direction;

public class CapabilityUtil {
	public static <T> LazyOptional<T> getCapability(Object o, Capability capability, Direction side) {
		return ((CapabilityProvider) o).getCapability(capability, side);
	}

	public static <T> LazyOptional<T> getCapability(Object o, Capability capability) {
		return getCapability(o, capability, null);
	}
}
