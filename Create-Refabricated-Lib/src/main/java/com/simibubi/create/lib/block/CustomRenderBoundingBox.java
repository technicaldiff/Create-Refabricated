package com.simibubi.create.lib.block;

import net.minecraft.util.math.AxisAlignedBB;

/**
 * Nothing here is actually used, it's just in case we figure something out in the future. see WorldRendererMixin.
 */
public interface CustomRenderBoundingBox {
	default AxisAlignedBB getRenderBoundingBox() {
		return getInfiniteBoundingBox();
	}

	default AxisAlignedBB getInfiniteBoundingBox() {
		return new AxisAlignedBB(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}
}
