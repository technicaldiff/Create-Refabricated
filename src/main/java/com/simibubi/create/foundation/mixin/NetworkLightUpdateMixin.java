package com.simibubi.create.foundation.mixin;

import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import com.simibubi.create.foundation.render.backend.RenderWork;
import com.simibubi.create.foundation.render.backend.light.LightListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class NetworkLightUpdateMixin {

	@Inject(at = @At("TAIL"), method = "onLightUpdate")
	private void onLightPacket(LightUpdateS2CPacket packet, CallbackInfo ci) {
		RenderWork.enqueue(() -> {
			ClientWorld world = MinecraftClient.getInstance().world;

			if (world == null) return;

			int chunkX = packet.getChunkX();
			int chunkZ = packet.getChunkZ();

			WorldChunk chunk = world.getChunkManager().getWorldChunk(chunkX, chunkZ, false);

			if (chunk != null) {
				chunk.getBlockEntities()
					.values()
					.stream()
					.filter(tile -> tile instanceof LightListener)
					.map(tile -> (LightListener) tile)
					.forEach(LightListener::onChunkLightUpdate);
			}

			ContraptionRenderDispatcher.notifyLightPacket(world, chunkX, chunkZ);
		});
	}
}