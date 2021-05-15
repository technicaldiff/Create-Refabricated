package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.extensions.TileEntityExtensions;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHelper {
	public static final String EXTRA_DATA_KEY = "create_ExtraTileEntityData";

	public static CompoundNBT getExtraCustomData(TileEntity tileEntity) {
		return ((TileEntityExtensions) tileEntity).create$getExtraCustomData();
	}
}
