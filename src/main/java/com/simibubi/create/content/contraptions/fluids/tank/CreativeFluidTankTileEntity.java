package com.simibubi.create.content.contraptions.fluids.tank;

import java.util.List;
import java.util.function.Consumer;

import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.lib.lba.fluid.FluidStack;
import com.simibubi.create.lib.utility.FluidUtil;

import alexiil.mc.lib.attributes.Simulation;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class CreativeFluidTankTileEntity extends FluidTankTileEntity {

	public CreativeFluidTankTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	protected SmartFluidTank createInventory() {
		return new CreativeSmartFluidTank(getCapacityMultiplier(), this::onFluidStackChanged);
	}

	@Override
	public boolean addToGoggleTooltip(List<ITextComponent> tooltip, boolean isPlayerSneaking) {
		return false;
	}

	public static class CreativeSmartFluidTank extends SmartFluidTank {
		// helper method
		public int getTankCapacity(int tank) {
			return (int) FluidUtil.fluidAmountToMillibuckets(getMaxAmount_F());
		}

		public CreativeSmartFluidTank(int capacity, Consumer<FluidStack> updateCallback) {
			super(capacity, updateCallback);
		}

		@Override
		public int getFluidAmount() {
			return getInvFluid().isEmpty() ? 0 : getTankCapacity(0);
		}

		public void setContainedFluid(FluidStack fluidStack) {
			setFluid((FluidStack) fluidStack.copy());
			if (!fluidStack.isEmpty()) {
				FluidStack newStack = new FluidStack(getFluid().fluidKey, getTankCapacity(0));
				setFluid(newStack);
			}
			onContentsChanged();
		}

		@Override
		public int fill(FluidStack resource, Simulation action) {
			return resource.getAmount();
		}

		@Override
		public FluidStack drain(FluidStack resource, Simulation action) {
			return super.drain(resource, Simulation.SIMULATE);
		}

		@Override
		public FluidStack drain(int maxDrain, Simulation action) {
			return super.drain(maxDrain, Simulation.SIMULATE);
		}

	}

}
