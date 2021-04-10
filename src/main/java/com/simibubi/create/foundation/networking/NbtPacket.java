package com.simibubi.create.foundation.networking;

import com.simibubi.create.content.curiosities.symmetry.SymmetryWandItem;
import com.simibubi.create.content.curiosities.zapper.ZapperItem;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;

@Deprecated
public class NbtPacket implements C2SPacket {

	public ItemStack stack;
	public int slot;
	public Hand hand;

	public NbtPacket() {}

	public NbtPacket(ItemStack stack, Hand hand) {
		this(stack, -1);
		this.hand = hand;
	}

	public NbtPacket(ItemStack stack, int slot) {
		this.stack = stack;
		this.slot = slot;
		this.hand = Hand.MAIN_HAND;
	}

	public void read(PacketBuffer buffer) {
		stack = buffer.readItemStack();
		slot = buffer.readInt();
		hand = Hand.values()[buffer.readInt()];
	}

	public void write(PacketBuffer buffer) {
		buffer.writeItemStack(stack);
		buffer.writeInt(slot);
		buffer.writeInt(hand.ordinal());
	}

	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server
			.execute(() -> {
				if (player == null)
					return;
				if (!(stack.getItem() instanceof SymmetryWandItem || stack.getItem() instanceof ZapperItem)) {
					return;
				}
				stack.removeChildTag("AttributeModifiers");
				if (slot == -1) {
					ItemStack heldItem = player.getHeldItem(hand);
					if (heldItem.getItem() == stack.getItem()) {
						heldItem.setTag(stack.getTag());
					}
					return;
				}

				ItemStack heldInSlot = player.inventory.getStackInSlot(slot);
				if (heldInSlot.getItem() == stack.getItem()) {
					heldInSlot.setTag(stack.getTag());
				}

			});
	}

}
