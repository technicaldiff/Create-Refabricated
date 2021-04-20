package com.simibubi.create.lib.mixin.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.world.spawner.AbstractSpawner;

@Mixin(AbstractSpawner.class)
public interface AbstractSpawnerAccessor {
	@Accessor("potentialSpawns")
	List<WeightedSpawnerEntity> create$potentialSpawns();

	@Accessor("spawnData")
	WeightedSpawnerEntity create$spawnData();
}
