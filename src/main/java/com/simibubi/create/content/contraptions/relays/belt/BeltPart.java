// PORTED CREATE SOURCE

package com.simibubi.create.content.contraptions.relays.belt;

import net.minecraft.util.StringIdentifiable;

import com.simibubi.create.foundation.utility.Lang;

public enum BeltPart implements StringIdentifiable {
	START, MIDDLE, END, PULLEY;

	@Override
	public String asString() {
		return Lang.asId(name());
	}
}