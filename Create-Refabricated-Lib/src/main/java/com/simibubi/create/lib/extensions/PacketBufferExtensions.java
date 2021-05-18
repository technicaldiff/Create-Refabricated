package com.simibubi.create.lib.extensions;

public interface PacketBufferExtensions {
	void writeFluidStack(FluidStack stack);

	FluidStack readFluidStack();
}
