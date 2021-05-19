package com.simibubi.create.lib.extensions;

public interface ServerPlayerEntityExtensions {
	default boolean create$isFakePlayer() {
		return false;
	}

	void create$setFake(boolean fake);
}
