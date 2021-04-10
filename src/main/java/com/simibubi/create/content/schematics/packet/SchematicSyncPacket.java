package com.simibubi.create.content.schematics.packet;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.filtering.SchematicInstances;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.PlacementSettings;

public class SchematicSyncPacket implements C2SPacket {

	public int slot;
	public boolean deployed;
	public BlockPos anchor;
	public Rotation rotation;
	public Mirror mirror;

	protected SchematicSyncPacket() {}

	public SchematicSyncPacket(int slot, PlacementSettings settings,
			BlockPos anchor, boolean deployed) {
		this.slot = slot;
		this.deployed = deployed;
		this.anchor = anchor;
		this.rotation = settings.getRotation();
		this.mirror = settings.getMirror();
	}

	public void read(PacketBuffer buffer) {
		slot = buffer.readVarInt();
		deployed = buffer.readBoolean();
		anchor = buffer.readBlockPos();
		rotation = buffer.readEnumValue(Rotation.class);
		mirror = buffer.readEnumValue(Mirror.class);
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeVarInt(slot);
		buffer.writeBoolean(deployed);
		buffer.writeBlockPos(anchor);
		buffer.writeEnumValue(rotation);
		buffer.writeEnumValue(mirror);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null)
				return;
			ItemStack stack = ItemStack.EMPTY;
			if (slot == -1) {
				stack = player.getHeldItemMainhand();
			} else {
				stack = player.inventory.getStackInSlot(slot);
			}
			if (!AllItems.SCHEMATIC.isIn(stack)) {
				return;
			}
			CompoundNBT tag = stack.getOrCreateTag();
			tag.putBoolean("Deployed", deployed);
			tag.put("Anchor", NBTUtil.writeBlockPos(anchor));
			tag.putString("Rotation", rotation.name());
			tag.putString("Mirror", mirror.name());
			SchematicInstances.clearHash(stack);
		});
	}

}
