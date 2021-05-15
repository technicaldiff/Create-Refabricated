package com.simibubi.create.lib.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.simibubi.create.lib.helper.EntityHelper;
import com.simibubi.create.lib.helper.TileEntityHelper;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class ExtraDataUtil {
	private static final Logger LOGGER = LogManager.getLogger();

	public static CompoundNBT getExtraData(Object o) {
		if (o instanceof Entity) {
			return EntityHelper.getExtraCustomData((Entity) o);
		}

		if (o instanceof TileEntity) {
			return TileEntityHelper.getExtraCustomData((TileEntity) o);
		}

		LOGGER.warn("Attempted to get extra data of an object that cannot have extra data!");
		return new CompoundNBT();
	}
}
