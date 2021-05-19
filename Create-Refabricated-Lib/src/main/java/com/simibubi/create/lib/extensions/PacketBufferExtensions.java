package com.simibubi.create.lib.extensions;

import com.simibubi.create.lib.lba.fluid.FluidStack;

public interface PacketBufferExtensions {
	void writeFluidStack(FluidStack stack);

	FluidStack readFluidStack();
}
