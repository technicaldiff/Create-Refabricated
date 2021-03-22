package com.simibubi.create.content.contraptions.components.flywheel.engine;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.components.flywheel.FlywheelBlock;
import com.simibubi.create.content.contraptions.components.flywheel.FlywheelBlockEntity;
import com.simibubi.create.foundation.block.entity.BlockEntityBehaviour;
import com.simibubi.create.foundation.block.entity.SmartBlockEntity;

public class EngineBlockEntity extends SmartBlockEntity {

	public float appliedCapacity;
	public float appliedSpeed;
	protected FlywheelBlockEntity poweredWheel;

	public EngineBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
	}

	/*protected Box cachedBoundingBox;
	@Override
	@Environment(EnvType.CLIENT)
	public Box getRenderBoundingBox() {
		if (cachedBoundingBox == null) {
			cachedBoundingBox = super.getRenderBoundingBox().expand(1.5f);
		}
		return cachedBoundingBox;
	}*/

	@Override
	public void lazyTick() {
		super.lazyTick();
		if (world.isClient)
			return;
		if (poweredWheel != null && poweredWheel.isRemoved())
			poweredWheel = null;
		if (poweredWheel == null)
			attachWheel();
	}

	public void attachWheel() {
		Direction engineFacing = getCachedState().get(EngineBlock.FACING);
		BlockPos wheelPos = pos.offset(engineFacing, 2);
		BlockState wheelState = world.getBlockState(wheelPos);
		if (!AllBlocks.FLYWHEEL.getStateManager().getStates().contains(wheelState))
			return;
		Direction wheelFacing = wheelState.get(FlywheelBlock.HORIZONTAL_FACING);
		if (wheelFacing.getAxis() != engineFacing.rotateYClockwise().getAxis())
			return;
		if (FlywheelBlock.isConnected(wheelState)
				&& FlywheelBlock.getConnection(wheelState) != engineFacing.getOpposite())
			return;
		BlockEntity te = world.getBlockEntity(wheelPos);
		if (te.isRemoved())
			return;
		if (te instanceof FlywheelBlockEntity) {
			if (!FlywheelBlock.isConnected(wheelState))
				FlywheelBlock.setConnection(world, te.getPos(), te.getCachedState(), engineFacing.getOpposite());
			poweredWheel = (FlywheelBlockEntity) te;
			refreshWheelSpeed();
		}
	}

	public void detachWheel() {
		if (poweredWheel.isRemoved())
			return;
		poweredWheel.setRotation(0, 0);
		FlywheelBlock.setConnection(world, poweredWheel.getPos(), poweredWheel.getCachedState(), null);
	}

	@Override
	public void markRemoved() {
		if (poweredWheel != null)
			detachWheel();
		super.markRemoved();
	}

	protected void refreshWheelSpeed() {
		if (poweredWheel == null)
			return;
		poweredWheel.setRotation(appliedSpeed, appliedCapacity);
	}

}
