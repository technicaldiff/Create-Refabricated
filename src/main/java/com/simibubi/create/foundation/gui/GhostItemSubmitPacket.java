package com.simibubi.create.foundation.gui;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;

public class GhostItemSubmitPacket implements C2SPacket {

	private ItemStack item;
	private int slot;

	protected GhostItemSubmitPacket() {}

	public GhostItemSubmitPacket(ItemStack item, int slot) {
		this.item = item;
		this.slot = slot;
	}

	@Override
	public void read(PacketBuffer buffer) {
		item = buffer.readItemStack();
		slot = buffer.readInt();
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeItemStack(item);
		buffer.writeInt(slot);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server
			.execute(() -> {
				if (player == null)
					return;

				if (player.openContainer instanceof GhostItemContainer) {
					GhostItemContainer<?> c = (GhostItemContainer<?>) player.openContainer;
					c.ghostInventory.setStackInSlot(slot, item);
					c.getSlot(36 + slot).onSlotChanged();
				}

			});
	}

}
