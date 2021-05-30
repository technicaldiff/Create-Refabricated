package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.inventory.container.Slot;

@Mixin(Slot.class)
public interface SlotAccessor {
	@Accessor("slotIndex")
	int getSlotIndex();
}
