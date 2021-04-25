package com.simibubi.create.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public interface FluidPlaceBlockCallback {
	public static final Event<FluidPlaceBlockCallback> EVENT = EventFactory.createArrayBacked(FluidPlaceBlockCallback.class, callbacks -> (world, pos, state) -> {
		for (FluidPlaceBlockCallback callback : callbacks) {
			return callback.onFluidPlaceBlock(world, pos, state);
		}

		return null;
	});

	BlockState onFluidPlaceBlock(IWorld world, BlockPos pos, BlockState state);
}
