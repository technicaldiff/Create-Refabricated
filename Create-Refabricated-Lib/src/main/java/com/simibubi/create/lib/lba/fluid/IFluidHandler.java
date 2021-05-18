package com.simibubi.create.lib.lba.fluid;

import com.simibubi.create.lib.utility.FluidUtil;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.FixedFluidInvView;
import alexiil.mc.lib.attributes.fluid.FluidExtractable;
import alexiil.mc.lib.attributes.fluid.FluidInsertable;
import alexiil.mc.lib.attributes.fluid.filter.ConstantFluidFilter;

public interface IFluidHandler {
	FixedFluidInvView getFluidStorage();

	default int getTanks() {
		return getFluidStorage().getTankCount();
	}

	default FluidStack getFluidInTank(int tank) {
		return (FluidStack) getFluidStorage().getInvFluid(tank);
	}

	default int getTankCapacity(int tank) {
		return FluidUtil.fluidAmountToMillibuckets(getFluidStorage().getInvFluid(tank).getAmount_F());
	}

	default FluidStack drain(int amount, Simulation sim) {
		return (FluidStack) ((FluidExtractable) getFluidStorage()).attemptExtraction(ConstantFluidFilter.ANYTHING, FluidUtil.millibucketsToFluidAmount(amount), sim);
	}

	default FluidStack drain(FluidStack stack, Simulation sim) {
		return drain(FluidUtil.fluidAmountToMillibuckets(stack.getAmount_F()), sim);
	}

	default int fill(FluidStack stack, Simulation sim) {
		return FluidUtil.fluidAmountToMillibuckets(((FluidInsertable) getFluidStorage()).attemptInsertion(stack, sim).getAmount_F());
	}
}
