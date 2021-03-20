package com.simibubi.create.foundation.mixin.accessor;

import net.minecraft.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BeaconBlockEntity.class)
public interface BeaconBlockEntityAccessor {
	@Accessor("beamSegments")
	public List<BeaconBlockEntity.BeamSegment> getBeamSegments();
}
