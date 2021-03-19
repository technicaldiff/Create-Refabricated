package com.simibubi.create.content.contraptions.relays.gauge;

import java.util.List;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.base.Rotating;
import com.simibubi.create.content.contraptions.goggles.GogglesItem;
import com.simibubi.create.foundation.advancement.AllTriggers;
import com.simibubi.create.foundation.utility.ColorHelper;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class SpeedGaugeBlockEntity extends GaugeBlockEntity {

	public SpeedGaugeBlockEntity() {
		super(AllBlockEntities.SPEEDOMETER);
	}

	@Override
	public void onSpeedChanged(float prevSpeed) {
		super.onSpeedChanged(prevSpeed);
		float speed = Math.abs(getSpeed());
		float medium = Create.getConfig().kinetics.mediumSpeed;
		float fast = Create.getConfig().kinetics.fastSpeed;
		float max = Create.getConfig().kinetics.maxRotationSpeed;
		color = ColorHelper.mixColors(Rotating.SpeedLevel.of(speed).getColor(), 0xffffff, .25f);

		if (speed == 69)
			AllTriggers.triggerForNearbyPlayers(AllTriggers.SPEED_READ, world, pos, 6,
					GogglesItem::canSeeParticles);
		if (speed == 0) {
			dialTarget = 0;
			color = 0x333333;
		} else if (speed < medium) {
			dialTarget = MathHelper.lerp(speed / medium, 0, .45f);
		} else if (speed < fast) {
			dialTarget = MathHelper.lerp((speed - medium) / (fast - medium), .45f, .75f);
		} else {
			dialTarget = MathHelper.lerp((speed - fast) / (max - fast), .75f, 1.125f);
		}
		
		markDirty();
	}

	@Override
	public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
		super.addToGoggleTooltip(tooltip, isPlayerSneaking);

		tooltip.add(componentSpacing.copy().append(Lang.translate("gui.speedometer.title").formatted(Formatting.GRAY)));
		tooltip.add(componentSpacing.copy().append(Rotating.SpeedLevel.getFormattedSpeedText(speed, isOverStressed())));

		return true;
	}
}
