package com.simibubi.create.content.logistics.block.depot;

import java.util.List;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.lib.capabilities.Capability;
import com.simibubi.create.lib.utility.LazyOptional;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public class DepotTileEntity extends SmartTileEntity {

	DepotBehaviour depotBehaviour;

	public DepotTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
		behaviours.add(depotBehaviour = new DepotBehaviour(this));
		depotBehaviour.addSubBehaviours(behaviours);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return depotBehaviour.getItemCapability(cap, side);
		return super.getCapability(cap, side);
	}
}
