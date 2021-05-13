package com.simibubi.create.lib.utility;

import net.minecraft.block.BlockState;
import net.minecraft.tags.TagRegistry;
import net.minecraft.util.ResourceLocation;

// i have no idea if this class actually works, it errors for me because I can't run genSources, but I AW'd the superclass so it *should* work.
public class NamedTagWrapper<T> extends TagRegistry.NamedTag<T> {
	public NamedTagWrapper<T> tag;

	public NamedTagWrapper(ResourceLocation resourceLocation) {
		super(resourceLocation);
		tag = this;
	}

	public boolean matches(BlockState block) {
		return this.contains(block.getBlock());
	}
}
