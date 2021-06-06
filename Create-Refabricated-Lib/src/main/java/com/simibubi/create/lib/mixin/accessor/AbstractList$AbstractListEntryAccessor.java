package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.widget.list.AbstractList;

@Mixin(AbstractList.AbstractListEntry.class)
public interface AbstractList$AbstractListEntryAccessor<E extends AbstractList.AbstractListEntry<E>> {
	@Accessor("list")
	AbstractList<E> getList();
}
