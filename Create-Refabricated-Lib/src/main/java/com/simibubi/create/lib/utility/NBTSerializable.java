package com.simibubi.create.lib.utility;

import net.minecraft.nbt.CompoundNBT;

public interface NBTSerializable {
	CompoundNBT serializeNBT();

	void deserializeNBT(CompoundNBT nbt);
}
