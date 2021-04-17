package com.simibubi.create.lib.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface MinecartPassHandlerBlock {
	void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart);
}
