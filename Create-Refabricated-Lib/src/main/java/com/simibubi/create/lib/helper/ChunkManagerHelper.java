package com.simibubi.create.lib.helper;

import com.simibubi.create.lib.mixin.accessor.ChunkManagerAccessor;
import com.simibubi.create.lib.utility.MixinHelper;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ChunkManager;

public class ChunkManagerHelper {
	public static Long2ObjectLinkedOpenHashMap<ChunkHolder> getLoadedChunks(ChunkManager chunkManager) {
		return get(chunkManager).create$loadedChunks();
	}

	public static Long2ObjectLinkedOpenHashMap<ChunkHolder> getChunksToUnload(ChunkManager chunkManager) {
		return get(chunkManager).create$chunksToUnload();
	}

	public static void setImmutableLoadedChunksDirty(ChunkManager chunkManager, boolean v) {
		get(chunkManager).create$immutableLoadedChunksDirty(v);
	}

	public static void scheduleSave(ChunkManager chunkManager, long l, ChunkHolder chunkHolder) {
		get(chunkManager).create$scheduleSave(l, chunkHolder);
	}

	private static ChunkManagerAccessor get(ChunkManager chunkManager) {
		return MixinHelper.cast(chunkManager);
	}

	private ChunkManagerHelper() {}
}
