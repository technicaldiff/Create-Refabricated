// PORTED CREATE SOURCE

package com.simibubi.create.content.curiosities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CombustibleItem extends Item {
	private int burnTime = -1;

	public CombustibleItem(Settings properties) {
		super(properties);
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}


	public int getFuelTime(ItemStack itemStack) {
		return this.burnTime;
	}
}