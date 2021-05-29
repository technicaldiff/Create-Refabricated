package com.simibubi.create.lib.mixin.accessor;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.Heightmap;

@Mixin(ChunkStatus.class)
public interface ChunkStatusAccessor {
	@Invoker("<init>")
	static ChunkStatus newChunkStatus(String string, @Nullable ChunkStatus chunkStatus, int i, EnumSet<Heightmap.Type> enumSet, ChunkStatus.Type type, ChunkStatus.IGenerationWorker iGenerationWorker, ChunkStatus.ILoadingWorker iLoadingWorker) {
		throw new RuntimeException("mixin applying went wrong");
	}
}
