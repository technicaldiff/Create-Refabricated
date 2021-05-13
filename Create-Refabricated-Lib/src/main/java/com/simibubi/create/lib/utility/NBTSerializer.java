package com.simibubi.create.lib.utility;

import com.simibubi.create.lib.helper.EntityHelper;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

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

	public static void deserializeEntityNBT(Entity entity, CompoundNBT nbt) {
		entity.read(nbt);
	}

	public static CompoundNBT serializeTileEntityNBT(TileEntity entity) {
		CompoundNBT nbt = new CompoundNBT();
		entity.write(nbt);
		return nbt;
	}
}
