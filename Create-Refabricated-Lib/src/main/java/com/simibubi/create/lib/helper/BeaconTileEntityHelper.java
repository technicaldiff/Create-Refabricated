package com.simibubi.create.lib.helper;

import java.util.List;

import com.simibubi.create.lib.mixin.accessor.BeaconTileEntityAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.BeaconTileEntity.BeamSegment;

public final class BeaconTileEntityHelper {
	public static List<BeamSegment> getBeamSegments(BeaconTileEntity bte) {
		return get(bte).create$beamSegments();
	}

	private static BeaconTileEntityAccessor get(BeaconTileEntity bte) {
		return MixinHelper.cast(bte);
	}

	private BeaconTileEntityHelper() {}
}
