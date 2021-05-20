package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;

@Mixin(FireBlock.class)
public interface FireBlockAccessor {
	@Invoker("func_220274_q")
	int func_220274_q(BlockState blockState);
}
