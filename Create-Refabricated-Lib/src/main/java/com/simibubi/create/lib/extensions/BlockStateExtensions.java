package com.simibubi.create.lib.extensions;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockStateExtensions {
	boolean addRunningEffects(World world, BlockPos pos, Entity entity);
}
