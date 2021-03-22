package com.simibubi.create.content.contraptions.relays.belt;

import net.minecraft.util.StringIdentifiable;

import com.simibubi.create.foundation.utility.Lang;

public enum BeltSlope implements StringIdentifiable {
	HORIZONTAL, UPWARD, DOWNWARD, VERTICAL, SIDEWAYS;

	@Override
	public String asString() {
		return Lang.asId(name());
	}

	public boolean isDiagonal() {
		return this == UPWARD || this == DOWNWARD;
	}
}