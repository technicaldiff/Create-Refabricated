package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.simibubi.create.lib.extensions.BlockStateExtensions;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockState.class)
public abstract class BlockStateMixin implements BlockStateExtensions {

	@Override
	@Unique
	public boolean create$addRunningEffects(World world, BlockPos pos, Entity entity) {
		return true;
	}
}
