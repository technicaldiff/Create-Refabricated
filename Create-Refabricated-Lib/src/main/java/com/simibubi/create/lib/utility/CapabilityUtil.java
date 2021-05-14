package com.simibubi.create.lib.utility;

import com.simibubi.create.lib.capabilities.Capability;
import com.simibubi.create.lib.capabilities.CapabilityProvider;

public class CapabilityUtil {
	public static LazyOptional getCapability(Object o, Capability capability) {
		return ((CapabilityProvider) o).getCapability(capability);
	}
}
