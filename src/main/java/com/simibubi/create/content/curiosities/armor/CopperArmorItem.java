package com.simibubi.create.content.curiosities.armor;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class CopperArmorItem extends ArmorItem {

	public CopperArmorItem(EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(AllArmorMaterials.COPPER, p_i48534_2_, p_i48534_3_);
	}

	public boolean isWornBy(Entity entity) {
		for (ItemStack itemStack : entity.getArmorInventoryList())
			if (itemStack.getItem() == this)
				return true;
		return false;
	}

}
