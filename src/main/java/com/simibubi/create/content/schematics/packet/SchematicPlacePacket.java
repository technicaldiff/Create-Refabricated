// PORTED CREATE SOURCE

package com.simibubi.create.content.schematics.packet;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;

import com.simibubi.create.content.schematics.SchematicProcessor;
import com.simibubi.create.content.schematics.item.SchematicItem;

public class SchematicPlacePacket implements C2SPacket {

	public ItemStack stack;

	public SchematicPlacePacket(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public void read(PacketByteBuf buffer) {
		stack = buffer.readItemStack();
	}

	@Override
	public void write(PacketByteBuf buffer) {
		buffer.writeItemStack(stack);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null)
				return;
			Structure t = SchematicItem.loadSchematic(stack);
			StructurePlacementData settings = SchematicItem.getSettings(stack);
			if (player.isCreativeLevelTwoOp())
				settings.removeProcessor(SchematicProcessor.INSTANCE); // remove processor
			settings.setIgnoreEntities(false);
			t.place(player.getServerWorld(), NbtHelper.toBlockPos(stack.getTag().getCompound("Anchor")),
				settings, player.getRandom());
		});
	}

}
