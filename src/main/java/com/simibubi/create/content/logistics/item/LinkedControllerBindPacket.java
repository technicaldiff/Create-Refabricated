package com.simibubi.create.content.logistics.item;

import org.apache.commons.lang3.tuple.Pair;

import com.simibubi.create.content.logistics.RedstoneLinkNetworkHandler.Frequency;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.linked.LinkBehaviour;
import com.simibubi.create.lib.lba.item.ItemStackHandler;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class LinkedControllerBindPacket extends LinkedControllerPacketBase {

	private int button;
	private BlockPos linkLocation;

	protected LinkedControllerBindPacket() {}

	public LinkedControllerBindPacket(int button, BlockPos linkLocation) {
		super((BlockPos) null);
		this.button = button;
		this.linkLocation = linkLocation;
	}

//	public LinkedControllerBindPacket(PacketBuffer buffer) {
//		this.button = buffer.readVarInt();
//		this.linkLocation = buffer.readBlockPos();
//	}

	@Override
	public void read(PacketBuffer buf) {
		super.read(buf);
		button = buf.readVarInt();
		linkLocation = buf.readBlockPos();
	}

	@Override
	public void write(PacketBuffer buffer) {
		super.write(buffer);
		buffer.writeVarInt(button);
		buffer.writeBlockPos(linkLocation);
	}

	@Override
	protected void handleItem(ServerPlayerEntity player, ItemStack heldItem) {
		if (player.isSpectator())
			return;

		ItemStackHandler frequencyItems = LinkedControllerItem.getFrequencyItems(heldItem);
		LinkBehaviour linkBehaviour = TileEntityBehaviour.get(player.world, linkLocation, LinkBehaviour.TYPE);
		if (linkBehaviour == null)
			return;

		Pair<Frequency, Frequency> pair = linkBehaviour.getNetworkKey();
		frequencyItems.setStackInSlot(button * 2, pair.getKey()
			.getStack()
			.copy());
		frequencyItems.setStackInSlot(button * 2 + 1, pair.getValue()
			.getStack()
			.copy());

		heldItem.getTag().put("Items", frequencyItems.serializeNBT());
	}

	@Override
	protected void handleLectern(ServerPlayerEntity player, LecternControllerTileEntity lectern) { }

}
