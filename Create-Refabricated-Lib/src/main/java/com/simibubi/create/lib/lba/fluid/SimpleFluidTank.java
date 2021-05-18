package com.simibubi.create.lib.lba.fluid;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;

public class SimpleFluidTank extends SimpleFixedFluidInv {

	// amount of tanks, capacity per tank
	public SimpleFluidTank(int invSize, FluidAmount tankCapacity) {
		super(invSize, tankCapacity);
	}

}
