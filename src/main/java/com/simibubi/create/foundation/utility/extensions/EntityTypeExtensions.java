package com.simibubi.create.foundation.utility.extensions;

import net.fabricmc.fabric.api.util.TriState;

public interface EntityTypeExtensions {
	TriState getAlwaysUpdateVelocity();

	void setAlwaysUpdateVelocity(TriState value);
}
