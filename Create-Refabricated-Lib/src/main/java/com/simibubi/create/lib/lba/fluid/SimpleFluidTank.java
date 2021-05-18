package com.simibubi.create.lib.lba.fluid;

import com.simibubi.create.lib.utility.FluidUtil;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.filter.ConstantFluidFilter;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;

/**
 * Simple wrapper with helper methods
 */
public class SimpleFluidTank extends SimpleFixedFluidInv {

	// amount of tanks, capacity per tank
	public SimpleFluidTank(int invSize, FluidAmount tankCapacity) {
		super(invSize, tankCapacity);
	}

	public FluidVolume getInvFluid() {
		return tanks.get(0);
	}

	public void setFluid(FluidStack stack) {
		setInvFluid(0, stack, Simulation.ACTION);
	}

	public FluidStack getFluid() {
		return (FluidStack) getInvFluid();
	}

	public FluidAmount getMaxAmount_F() {
		return tankCapacity_F;
	}

	public int getFluidAmount() {
		return (int) FluidUtil.fluidAmountToMillibuckets(getInvFluid().amount());
	}

	public int fill(FluidStack resource, Simulation action) {
		return (int) FluidUtil.fluidAmountToMillibuckets(resource.getAmount_F());

	}

	public FluidStack drain(FluidStack resource, Simulation action) {
		return (FluidStack) attemptExtraction(ConstantFluidFilter.ANYTHING, resource.amount(), action);

	}

	public FluidStack drain(int maxDrain, Simulation action) {
		return (FluidStack) attemptExtraction(ConstantFluidFilter.ANYTHING, FluidUtil.millibucketsToFluidAmount(maxDrain), action);
	}
}
