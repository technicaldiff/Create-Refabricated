package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.AbstractSpawnerAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.world.spawner.AbstractSpawner;

import java.util.List;

public class AbstractSpawnerHelper {
	public static List<WeightedSpawnerEntity> getPotentialSpawns(AbstractSpawner abstractSpawner) {
		return get(abstractSpawner).create$potentialSpawns();
	}

	public static WeightedSpawnerEntity getSpawnData(AbstractSpawner abstractSpawner) {
		return get(abstractSpawner).create$spawnData();
	}

	private static AbstractSpawnerAccessor get(AbstractSpawner abstractSpawner) {
		return MixinHelper.cast(abstractSpawner);
	}

	private AbstractSpawnerHelper() {}
}
