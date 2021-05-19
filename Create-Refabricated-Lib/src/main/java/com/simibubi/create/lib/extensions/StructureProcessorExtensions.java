package com.simibubi.create.lib.extensions;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

public interface StructureProcessorExtensions {
	default Template.EntityInfo processEntity(IWorldReader world, BlockPos seedPos, Template.EntityInfo rawEntityInfo, Template.EntityInfo entityInfo, PlacementSettings placementSettings, Template template) {
		return entityInfo;
	}
}
