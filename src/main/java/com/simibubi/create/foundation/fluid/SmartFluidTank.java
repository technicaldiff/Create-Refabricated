package com.simibubi.create.foundation.fluid;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;

import com.simibubi.create.lib.lba.fluid.FluidStack;
import com.simibubi.create.lib.lba.fluid.SimpleFluidTank;
import com.simibubi.create.lib.utility.FluidUtil;

import java.util.function.Consumer;


public class SmartFluidTank extends SimpleFluidTank {

	private Consumer<FluidStack> updateCallback;

	public SmartFluidTank(int capacity, Consumer<FluidStack> updateCallback) {
		super(1, FluidUtil.millibucketsToFluidAmount(capacity));
		this.updateCallback = updateCallback;
	}

	@Override
	protected void onContentsChanged() {
		super.onContentsChanged();
		updateCallback.accept(getFluid());
	}

	@Override
	public void setFluid(FluidStack stack) {
		super.setFluid(stack);
		updateCallback.accept(stack);
	}
}
