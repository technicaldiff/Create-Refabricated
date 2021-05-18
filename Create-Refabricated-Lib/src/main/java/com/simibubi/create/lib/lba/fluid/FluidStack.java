package com.simibubi.create.lib.lba.fluid;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.simibubi.create.lib.utility.FluidUtil;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;

/**
 * Wrapper for FluidVolume to minimize needed changes
 */
public class FluidStack extends FluidVolume {
	public static final FluidStack EMPTY = new FluidStack(FluidKeys.EMPTY, FluidAmount.ZERO);

	public FluidStack(FluidKey key, FluidAmount amount) {
		super(key, amount);
	}

	public FluidStack(FluidKey key, CompoundNBT tag) {
		super(key, tag);
	}

	public FluidStack(FluidKey key, int amount) {
		super(key, FluidUtil.millibucketsToFluidAmount(amount));
	}

	public FluidStack(Fluid key, int amount) {
		super(FluidKeys.get(key), FluidUtil.millibucketsToFluidAmount(amount));
	}

	public FluidStack(FluidKey key, JsonObject json) throws JsonSyntaxException {
		super(key, json);
	}

	public Fluid getFluid() {
		return getRawFluid();
	}

	public boolean isLighterThanAir() {
		return FluidUtil.isLighterThanAir(this);
	}

	public boolean isFluidStackIdentical(FluidStack other) {
		return this.getRawFluid() == other.getRawFluid() && this.amount() == other.amount();
	}

	public static FluidStack loadFluidStackFromNBT(CompoundNBT nbt) {
		return (FluidStack) fromTag(nbt);
	}

	public void setAmount(int amount, String... parameterToPreventOverriding) {
		this.setAmount(FluidUtil.millibucketsToFluidAmount(amount));
	}

	public CompoundNBT writeToNBT(CompoundNBT nbt) {
		return toTag(nbt);
	}

	public String getTranslationKey() {
		return "todo"; // todo
	}

	public boolean isFluidEqual(FluidStack other) {
		return getRawFluid() == other.getRawFluid() && toTag() == other.toTag();
	}
}
