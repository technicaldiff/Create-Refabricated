package com.simibubi.create.lib.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.simibubi.create.lib.utility.LazyOptional;

import net.minecraft.util.Direction;

public interface CapabilityProvider {
	@Nonnull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction);

	@Nonnull default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability) {
		return getCapability(capability, null);
	}
}
