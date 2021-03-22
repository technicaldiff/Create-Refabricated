package com.simibubi.create.content.logistics.block.belts.tunnel;

import net.minecraft.util.math.Vec3d;

import com.simibubi.create.foundation.block.entity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.VecHelper;

public class BrassTunnelFilterSlot extends ValueBoxTransform.Sided {

	@Override
	protected Vec3d getSouthLocation() {
		return VecHelper.voxelSpace(8, 13, 15.5f);
	}

}
