package com.simibubi.create.content.contraptions.components.flywheel.engine;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class FurnaceEngineBlockEntity extends EngineBlockEntity {

	public FurnaceEngineBlockEntity() {
		super(AllBlockEntities.FURNACE_ENGINE);
	}

	@Override
	public void lazyTick() {
		updateFurnace();
		super.lazyTick();
	}

	public void updateFurnace() {
		BlockState state = world.getBlockState(EngineBlock.getBaseBlockPos(getCachedState(), pos));
		if (!(state.getBlock() instanceof AbstractFurnaceBlock))
			return;

		float modifier = state.getBlock() == Blocks.BLAST_FURNACE ? 2 : 1;
		boolean active = BlockHelper.hasBlockStateProperty(state, AbstractFurnaceBlock.LIT) && state.get(AbstractFurnaceBlock.LIT);
		float speed = active ? 16 * modifier : 0;
		float capacity =
			(float) (active ? Create.getConfig().stress.furnaceEngine
				: 0);

		appliedCapacity = capacity;
		appliedSpeed = speed;
		refreshWheelSpeed();
	}

}
