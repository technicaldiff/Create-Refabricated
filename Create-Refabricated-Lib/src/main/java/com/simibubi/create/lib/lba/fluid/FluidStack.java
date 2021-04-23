package com.simibubi.create.lib.lba.fluid;

import java.io.IOException;

import alexiil.mc.lib.attributes.fluid.FluidVolumeUtil;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class FluidStack {

	public static FluidStack EMPTY = new FluidStack(FluidVolumeUtil.EMPTY);

	private FluidVolume volume;

	public FluidStack(FluidVolume v) {
		volume = v;
	}

	public FluidStack(Fluid fluid, int amount) {
		volume = FluidKeys.get(fluid).withAmount(FluidAmount.of1620(amount));
	}

	public Fluid getFluid() {
		return volume.getRawFluid();
	}

	public int getAmount() {
		return volume.getAmount_F().as1620();
	}

	public FluidStack withAmount(int amount) {
		return new FluidStack(volume.withAmount(FluidAmount.of1620(amount)));
	}

	public boolean isEmpty() {
		return volume.isEmpty();
	}

	public void shrink(int amount) {
		volume = volume.withAmount(FluidAmount.of1620(getAmount() - amount));
	}

	public FluidStack copy() {
		return new FluidStack(volume.copy());
	}

	public void setTag(CompoundNBT nbt) {
		volume = FluidVolume.fromTag(nbt);
	}

	public CompoundNBT getTag() {
		return volume.toTag();
	}

	public static FluidStack readFromPacket(PacketBuffer buffer) {
		try {
			return new FluidStack(FluidVolume.fromMcBuffer(buffer));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void writeToPacket(PacketBuffer buffer) {
		volume.toMcBuffer(buffer);
	}
}
