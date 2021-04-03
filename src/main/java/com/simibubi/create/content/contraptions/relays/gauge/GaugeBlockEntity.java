// PORTED CREATE SOURCE

package com.simibubi.create.content.contraptions.relays.gauge;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import com.simibubi.create.content.contraptions.base.KineticBlockEntity;
import com.simibubi.create.content.contraptions.goggles.GoggleInformationProvider;
import com.simibubi.create.foundation.utility.Lang;

public class GaugeBlockEntity extends KineticBlockEntity implements GoggleInformationProvider {

	public float dialTarget;
	public float dialState;
	public float prevDialState;
	public int color;

	public GaugeBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public void toTag(CompoundTag compound, boolean clientPacket) {
		compound.putFloat("Value", dialTarget);
		compound.putInt("Color", color);
		super.toTag(compound, clientPacket);
	}

	@Override
	protected void fromTag(BlockState state, CompoundTag compound, boolean clientPacket) {
		dialTarget = compound.getFloat("Value");
		color = compound.getInt("Color");
		super.fromTag(state, compound, clientPacket);
	}

	@Override
	public void tick() {
		super.tick();
		prevDialState = dialState;
		dialState += (dialTarget - dialState) * .125f;
		if (dialState > 1 && world.random.nextFloat() < 1 / 2f)
			dialState -= (dialState - 1) * world.random.nextFloat();
	}

	@Override
	public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
		tooltip.add(componentSpacing.copy().append(Lang.translate("gui.gauge.info_header")));

		return true;
	}

	@Override
	public boolean shouldRenderAsBE() {
		return true;
	}
}
