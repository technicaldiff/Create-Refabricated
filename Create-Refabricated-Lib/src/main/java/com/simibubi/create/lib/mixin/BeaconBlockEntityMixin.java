package com.simibubi.create.lib.mixin;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.entity.BeaconBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.SERVER)
@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {
	@Shadow
	private int level;
	@Shadow
	private List<BeaconBlockEntity.BeamSegment> beamSegments;

	public List<BeaconBlockEntity.BeamSegment> getBeamSegments() {
		return this.level == 0 ? ImmutableList.of() : this.beamSegments;
	}

	public List<BeaconBlockEntity.BeamSegment> method_10937() {
		return getBeamSegments();
	}
}
