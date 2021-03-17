package com.simibubi.create.content.schematics.packet;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.filtering.SchematicInstances;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;

public class SchematicSyncPacket implements C2SPacket {

	public int slot;
	public boolean deployed;
	public BlockPos anchor;
	public BlockRotation rotation;
	public BlockMirror mirror;

	public SchematicSyncPacket(int slot, StructurePlacementData settings,
			BlockPos anchor, boolean deployed) {
		this.slot = slot;
		this.deployed = deployed;
		this.anchor = anchor;
		this.rotation = settings.getRotation();
		this.mirror = settings.getMirror();
	}

	@Override
	public void read(PacketByteBuf buffer) {
		slot = buffer.readVarInt();
		deployed = buffer.readBoolean();
		anchor = buffer.readBlockPos();
		rotation = buffer.readEnumConstant(BlockRotation.class);
		mirror = buffer.readEnumConstant(BlockMirror.class);
	}

	@Override
	public void write(PacketByteBuf buffer) {
		buffer.writeVarInt(slot);
		buffer.writeBoolean(deployed);
		buffer.writeBlockPos(anchor);
		buffer.writeEnumConstant(rotation);
		buffer.writeEnumConstant(mirror);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null)
				return;
			ItemStack stack = ItemStack.EMPTY;
			if (slot == -1) {
				stack = player.getMainHandStack();
			} else {
				stack = player.inventory.getStack(slot);
			}
			if (!stack.isItemEqualIgnoreDamage(AllItems.SCHEMATIC.getDefaultStack())) {
				return;
			}
			CompoundTag tag = stack.getOrCreateTag();
			tag.putBoolean("Deployed", deployed);
			tag.put("Anchor", NbtHelper.fromBlockPos(anchor));
			tag.putString("Rotation", rotation.name());
			tag.putString("Mirror", mirror.name());
			SchematicInstances.clearHash(stack);
		});
	}

}
