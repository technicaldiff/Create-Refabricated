package com.simibubi.create.foundation.mixin.accessor;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StairsBlock.class)
public interface StairsBlockAccessor {
	@Invoker("<init>")
	static StairsBlock invokeInit(BlockState baseBlockState, AbstractBlock.Settings settings) {
		throw new AssertionError();
	}
}
