package com.simibubi.create.lib.utility;

import net.minecraft.nbt.CompoundNBT;

public class NBTSerializer {
	public static void deserializeNBT(Object o, CompoundNBT nbt) {
		((NBTSerializable) o).create$deserializeNBT(nbt);
	}

	public static CompoundNBT serializeNBT(Object o) {
		return ((NBTSerializable) o).create$serializeNBT();
	}
}
