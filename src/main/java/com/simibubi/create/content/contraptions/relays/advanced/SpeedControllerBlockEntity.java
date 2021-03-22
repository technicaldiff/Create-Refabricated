package com.simibubi.create.content.contraptions.relays.advanced;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.RotationPropagator;
import com.simibubi.create.content.contraptions.base.KineticBlockEntity;
import com.simibubi.create.content.contraptions.components.motor.CreativeMotorBlockEntity;
import com.simibubi.create.content.contraptions.relays.elementary.CogWheelBlock;
import com.simibubi.create.foundation.block.entity.BlockEntityBehaviour;
import com.simibubi.create.foundation.block.entity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.block.entity.behaviour.scrollvalue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;

public class SpeedControllerBlockEntity extends KineticBlockEntity {

	public static final int DEFAULT_SPEED = 16;
	protected ScrollValueBehaviour targetSpeed;

	boolean hasBracket;

	public SpeedControllerBlockEntity() {
		super(AllBlockEntities.ROTATION_SPEED_CONTROLLER);
		hasBracket = false;
	}

	@Override
	public void lazyTick() {
		super.lazyTick();
		updateBracket();
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		super.addBehaviours(behaviours);
		Integer max = Create.getConfig().kinetics.maxRotationSpeed;

		targetSpeed =
			new ScrollValueBehaviour(Lang.translate("generic.speed"), this, new ControllerValueBoxTransform());
		targetSpeed.between(-max, max);
		targetSpeed.value = DEFAULT_SPEED;
		targetSpeed.moveText(new Vec3d(9, 0, 10));
		targetSpeed.withUnit(i -> Lang.translate("generic.unit.rpm"));
		targetSpeed.withCallback(i -> this.updateTargetRotation());
		targetSpeed.withStepFunction(CreativeMotorBlockEntity::step);
		behaviours.add(targetSpeed);
	}

	private void updateTargetRotation() {
		if (hasNetwork())
			getOrCreateNetwork().remove(this);
		RotationPropagator.handleRemoved(world, pos, this);
		removeSource();
		attachKinetics();
	}

	public static float getConveyedSpeed(KineticBlockEntity cogWheel, KineticBlockEntity speedControllerIn,
		boolean targetingController) {
		if (!(speedControllerIn instanceof SpeedControllerBlockEntity))
			return 0;

		float speed = speedControllerIn.getTheoreticalSpeed();
		float wheelSpeed = cogWheel.getTheoreticalSpeed();
		float desiredOutputSpeed = getDesiredOutputSpeed(cogWheel, speedControllerIn, targetingController);

		float compareSpeed = targetingController ? speed : wheelSpeed;
		if (desiredOutputSpeed >= 0 && compareSpeed >= 0)
			return Math.max(desiredOutputSpeed, compareSpeed);
		if (desiredOutputSpeed < 0 && compareSpeed < 0)
			return Math.min(desiredOutputSpeed, compareSpeed);

		return desiredOutputSpeed;
	}

	public static float getDesiredOutputSpeed(KineticBlockEntity cogWheel, KineticBlockEntity speedControllerIn,
		boolean targetingController) {
		SpeedControllerBlockEntity speedController = (SpeedControllerBlockEntity) speedControllerIn;
		float targetSpeed = speedController.targetSpeed.getValue();
		float speed = speedControllerIn.getTheoreticalSpeed();
		float wheelSpeed = cogWheel.getTheoreticalSpeed();

		if (targetSpeed == 0)
			return 0;
		if (targetingController && wheelSpeed == 0)
			return 0;
		if (!speedController.hasSource()) {
			if (targetingController)
				return targetSpeed;
			return 0;
		}

		boolean wheelPowersController = speedController.source.equals(cogWheel.getPos());

		if (wheelPowersController) {
			if (targetingController)
				return targetSpeed;
			return wheelSpeed;
		}

		if (targetingController)
			return speed;
		return targetSpeed;
	}

	public void updateBracket() {
		if (world == null || !world.isClient)
			return;
		BlockState stateAbove = world.getBlockState(pos.up());
		hasBracket = AllBlocks.LARGE_COGWHEEL.getStateManager().getStates().contains(stateAbove) && stateAbove.get(CogWheelBlock.AXIS)
			.isHorizontal();
	}

	private class ControllerValueBoxTransform extends ValueBoxTransform.Sided {

		@Override
		protected Vec3d getSouthLocation() {
			return VecHelper.voxelSpace(8, 11f, 16);
		}

		@Override
		protected boolean isSideActive(BlockState state, Direction direction) {
			if (direction.getAxis()
				.isVertical())
				return false;
			return state.get(SpeedControllerBlock.HORIZONTAL_AXIS) != direction.getAxis();
		}

		@Override
		protected float getScale() {
			return 0.275f;
		}

	}

}
