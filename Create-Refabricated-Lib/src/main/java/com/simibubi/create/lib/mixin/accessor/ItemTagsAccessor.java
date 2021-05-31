package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

@Mixin(ItemTags.class)
public interface ItemTagsAccessor {
	@Invoker("makeWrapperTag")
	public static ITag.INamedTag<Item> makeWrapperTag(String string) {
		throw new RuntimeException("something went wrong applying mixins");
	}
}
