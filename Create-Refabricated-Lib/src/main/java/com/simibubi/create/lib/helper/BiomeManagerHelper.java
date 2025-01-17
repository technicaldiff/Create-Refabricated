package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.BiomeManagerAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.world.biome.BiomeManager;

public final class BiomeManagerHelper {
	public static long getSeed(BiomeManager biomeManager) {
		return get(biomeManager).create$seed();
	}

	private static BiomeManagerAccessor get(BiomeManager biomeManager) {
		return MixinHelper.cast(biomeManager);
	}

	private BiomeManagerHelper() {}
}
