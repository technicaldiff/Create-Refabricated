package com.simibubi.create.lib.utility;

import net.minecraft.nbt.CompoundNBT;

public interface NBTSerializable {
	CompoundNBT create$serializeNBT();

	void create$deserializeNBT(CompoundNBT nbt);
}
