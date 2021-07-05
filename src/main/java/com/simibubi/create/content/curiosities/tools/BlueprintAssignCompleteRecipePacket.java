package com.simibubi.create.content.curiosities.tools;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class BlueprintAssignCompleteRecipePacket implements C2SPacket {

	private ResourceLocation recipeID;

	protected BlueprintAssignCompleteRecipePacket() {}

	public BlueprintAssignCompleteRecipePacket(ResourceLocation recipeID) {
		this.recipeID = recipeID;
	}

	@Override
	public void read(PacketBuffer buffer) {
		recipeID = buffer.readResourceLocation();
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeResourceLocation(recipeID);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server
				.execute(() -> {
					if (player == null)
						return;
					if (player.openContainer instanceof BlueprintContainer) {
						BlueprintContainer c = (BlueprintContainer) player.openContainer;
						player.getServerWorld()
								.getRecipeManager()
								.getRecipe(recipeID)
								.ifPresent(r -> BlueprintItem.assignCompleteRecipe(c.ghostInventory, r));
					}
				});
	}

}
