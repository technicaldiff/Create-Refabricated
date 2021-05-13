package com.simibubi.create.lib.utility;

import com.simibubi.create.lib.helper.EntityHelper;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class NBTSerializer {
	public static CompoundNBT serializeItemStackNBT(ItemStack stack) {
		CompoundNBT nbt = new CompoundNBT();
		stack.write(nbt);
		return nbt;
	}

	public static CompoundNBT serializeEntityNBT(Entity entity) {
		CompoundNBT nbt = new CompoundNBT();
		String id = EntityHelper.getEntityString(entity);

		if (id != null) {
			nbt.putString("id", id);
		}

		return nbt;
	}
}
