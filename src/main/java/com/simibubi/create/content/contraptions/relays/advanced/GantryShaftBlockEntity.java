package com.simibubi.create.content.contraptions.relays.advanced;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticBlockEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.gantry.GantryPinionBlock;
import com.simibubi.create.content.contraptions.components.structureMovement.gantry.GantryPinionBlockEntity;
import com.simibubi.create.foundation.utility.Iterate;

public class GantryShaftBlockEntity extends KineticBlockEntity {

	public GantryShaftBlockEntity() {
		super(AllBlockEntities.GANTRY_SHAFT);
	}

	@Override
	public void onSpeedChanged(float previousSpeed) {
		super.onSpeedChanged(previousSpeed);

		if (!canAssembleOn())
			return;
		for (Direction d : Iterate.directions) {
			if (d.getAxis() == getCachedState().get(GantryShaftBlock.FACING)
				.getAxis())
				continue;
			BlockPos offset = pos.offset(d);
			BlockState pinionState = world.getBlockState(offset);
			if (!AllBlocks.GANTRY_PINION.getStateManager().getStates().contains(pinionState))
				continue;
			if (pinionState.get(GantryPinionBlock.FACING) != d)
				continue;
			BlockEntity tileEntity = world.getBlockEntity(offset);
			if (tileEntity instanceof GantryPinionBlockEntity)
				((GantryPinionBlockEntity) tileEntity).queueAssembly();
		}

	}

	@Override
	public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff,
		boolean connectedViaAxes, boolean connectedViaCogs) {
		float defaultModifier =
			super.propagateRotationTo(target, stateFrom, stateTo, diff, connectedViaAxes, connectedViaCogs);

		if (connectedViaAxes)
			return defaultModifier;
		if (!stateFrom.get(GantryShaftBlock.POWERED))
			return defaultModifier;
		if (!AllBlocks.GANTRY_PINION.getStateManager().getStates().contains(stateTo))
			return defaultModifier;

		Direction direction = Direction.getFacing(diff.getX(), diff.getY(), diff.getZ());
		if (stateTo.get(GantryPinionBlock.FACING) != direction)
			return defaultModifier;
		return GantryPinionBlockEntity.getGantryPinionModifier(stateFrom.get(GantryShaftBlock.FACING),
			stateTo.get(GantryPinionBlock.FACING));
	}

	@Override
	public boolean isCustomConnection(KineticBlockEntity other, BlockState state, BlockState otherState) {
		if (!AllBlocks.GANTRY_PINION.getStateManager().getStates().contains(otherState))
			return false;
		final BlockPos diff = other.getPos()
			.subtract(pos);
		Direction direction = Direction.getFacing(diff.getX(), diff.getY(), diff.getZ());
		return otherState.get(GantryPinionBlock.FACING) == direction;
	}

	public boolean canAssembleOn() {
		BlockState blockState = getCachedState();
		if (!AllBlocks.GANTRY_SHAFT.getStateManager().getStates().contains(blockState))
			return false;
		if (blockState.get(GantryShaftBlock.POWERED))
			return false;
		float speed = getPinionMovementSpeed();

		switch (blockState.get(GantryShaftBlock.PART)) {
		case END:
			return speed < 0;
		case MIDDLE:
			return speed != 0;
		case START:
			return speed > 0;
		case SINGLE:
		default:
			return false;
		}
	}

	public float getPinionMovementSpeed() {
		BlockState blockState = getCachedState();
		if (!AllBlocks.GANTRY_SHAFT.getStateManager().getStates().contains(blockState))
			return 0;
		return MathHelper.clamp(-getSpeed() / 512f, -.49f, .49f);
	}

}
