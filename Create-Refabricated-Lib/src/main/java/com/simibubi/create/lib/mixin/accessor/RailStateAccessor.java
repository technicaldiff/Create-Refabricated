package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.RailState;
import net.minecraft.util.math.BlockPos;

@Mixin(RailState.class)
public interface RailStateAccessor {
	@Accessor("pos")
	BlockPos create$pos();

	@Invoker("checkConnected")
	void create$checkConnected();

	@Invoker("func_196905_c")
	boolean create$func_196905_c(RailState railState);
}
