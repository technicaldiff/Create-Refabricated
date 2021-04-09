package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

@Mixin(StairsBlock.class)
public interface StairsBlockAccessor {
	@Invoker("<init>")
	static StairsBlock create$init(BlockState baseBlockState, AbstractBlock.Settings settings) {
		throw new AssertionError();
	}
}
