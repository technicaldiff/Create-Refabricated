package com.simibubi.create.content.contraptions.components.fan;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.config.CKinetics;
import com.simibubi.create.registrate.util.nullness.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
public interface AirCurrentSource {
	@Nullable
	AirCurrent getAirCurrent();

	@Nullable
	World getAirCurrentWorld();

	BlockPos getAirCurrentPos();

	float getSpeed();

	Direction getAirflowOriginSide();

	@Nullable
	Direction getAirFlowDirection();

	default float getMaxDistance() {
		float speed = Math.abs(this.getSpeed());
		CKinetics config = Create.getConfig().kinetics;
		float distanceFactor = Math.min(speed / config.fanRotationArgmax, 1);
		float pushDistance = MathHelper.lerp(distanceFactor, 3, config.fanPushDistance);
		float pullDistance = MathHelper.lerp(distanceFactor, 3f, config.fanPullDistance);
		return this.getSpeed() > 0 ? pushDistance : pullDistance;
	}

	boolean isSourceRemoved();
}
