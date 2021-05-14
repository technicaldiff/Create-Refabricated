package com.simibubi.create.lib.utility;

import net.minecraft.block.BlockState;
import net.minecraft.tags.TagRegistry.NamedTag;
import net.minecraft.util.ResourceLocation;

public class NamedTagWrapper<T> extends NamedTag<T> {
	public NamedTagWrapper<T> tag;

	public NamedTagWrapper(ResourceLocation resourceLocation) {
		super(resourceLocation);
		tag = this;
	}

	public boolean matches(BlockState block) {
		return this.contains(block.getBlock());
	}
}
