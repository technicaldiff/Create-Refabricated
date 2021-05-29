package com.simibubi.create.foundation.command;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.simibubi.create.lib.helper.ChunkManagerHelper;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class ChunkUtil {
	private static final Logger LOGGER = LogManager.getLogger("Create/ChunkUtil");
	public final EnumSet<Heightmap.Type> POST_FEATURES = EnumSet.of(Heightmap.Type.OCEAN_FLOOR, Heightmap.Type.WORLD_SURFACE,
		Heightmap.Type.MOTION_BLOCKING, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);

	public final List<Long> markedChunks;
	private final List<Long> interestingChunks;

	public ChunkUtil() {
		LOGGER.debug("Chunk Util constructed");
		markedChunks = new LinkedList<>();
		interestingChunks = new LinkedList<>();
	}

	public void init() {
		// now done via mixin crimes, ChunkStatusMixin
//		ChunkStatus.FULL =
//			new ChunkStatus("full", ChunkStatus.HEIGHTMAPS, 0, POST_FEATURES, ChunkStatus.Type.LEVELCHUNK,
//				(_0, _1, _2, _3, _4, future, _6, chunk) -> future.apply(chunk), (_0, _1, _2, _3, future, chunk) -> {
//					if (markedChunks.contains(chunk.getPos()
//						.asLong())) {
//						LOGGER.debug("trying to load unforced chunk " + chunk.getPos()
//							.toString() + ", returning chunk loading error");
//						// this.reloadChunk(world.getChunkProvider(), chunk.getPos());
//						return ChunkHolder.MISSING_CHUNK_FUTURE;
//					} else {
//						// LOGGER.debug("regular, chunkStatus: " + chunk.getStatus().toString());
//						return future.apply(chunk);
//					}
//				});

		ServerChunkEvents.CHUNK_LOAD.register(this::chunkLoad);
		ServerChunkEvents.CHUNK_UNLOAD.register(this::chunkUnload);
	}

	public boolean reloadChunk(ServerChunkProvider provider, ChunkPos pos) {
		ChunkHolder holder = ChunkManagerHelper.getLoadedChunks(provider.chunkManager).remove(pos.asLong());
		ChunkManagerHelper.setImmutableLoadedChunksDirty(provider.chunkManager, true);
		if (holder != null) {
			ChunkManagerHelper.getChunksToUnload(provider.chunkManager).put(pos.asLong(), holder);
			ChunkManagerHelper.scheduleSave(provider.chunkManager, pos.asLong(), holder);
			return true;
		} else {
			return false;
		}
	}

	public boolean unloadChunk(ServerChunkProvider provider, ChunkPos pos) {
		this.interestingChunks.add(pos.asLong());
		this.markedChunks.add(pos.asLong());

		return this.reloadChunk(provider, pos);
	}

	public int clear(ServerChunkProvider provider) {
		LinkedList<Long> copy = new LinkedList<>(this.markedChunks);

		int size = this.markedChunks.size();
		this.markedChunks.clear();

		copy.forEach(l -> reForce(provider, new ChunkPos(l)));

		return size;
	}

	public void reForce(ServerChunkProvider provider, ChunkPos pos) {
		provider.forceChunk(pos, true);
		provider.forceChunk(pos, false);
	}

	public void chunkUnload(ServerWorld serverWorld, Chunk chunk) {
		// LOGGER.debug("Chunk Unload: " + event.getChunk().getPos().toString());
		if (interestingChunks.contains(chunk
			.getPos()
			.asLong())) {
			LOGGER.info("Interesting Chunk Unload: " + chunk
				.getPos()
				.toString());
		}
	}

	public void chunkLoad(ServerWorld serverWorld, Chunk chunk) {
		// LOGGER.debug("Chunk Load: " + event.getChunk().getPos().toString());

		ChunkPos pos = chunk
			.getPos();
		if (interestingChunks.contains(pos.asLong())) {
			LOGGER.info("Interesting Chunk Load: " + pos.toString());
			if (!markedChunks.contains(pos.asLong()))
				interestingChunks.remove(pos.asLong());
		}

	}

}
