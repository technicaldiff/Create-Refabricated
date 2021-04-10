package com.simibubi.create.lib.mixin.accessor;

import java.util.List;
import net.minecraft.tileentity.BeaconTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BeaconTileEntity.class)
public interface BeaconTileEntityAccessor {
	@Accessor("beamSegments")
	List<BeaconTileEntity.BeamSegment> create$beamSegments();
}
