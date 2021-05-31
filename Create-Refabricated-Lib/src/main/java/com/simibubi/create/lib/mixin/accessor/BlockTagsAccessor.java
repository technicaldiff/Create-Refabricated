package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

@Mixin(BlockTags.class)
public interface BlockTagsAccessor {
	@Invoker("makeWrapperTag")
	static ITag.INamedTag<Block> makeWrapperTag(String string) {
		throw new RuntimeException("something went wrong applying mixins");
	}
}
