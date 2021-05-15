package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(Block.class)
public interface BlockAccessor {
	@Invoker("dropXpOnBlockBreak")
	void create$dropXpOnBlockBreak(ServerWorld serverWorld, BlockPos blockPos, int i);
}
