package com.simibubi.create.content.logistics.block.mechanicalArm;

import java.util.Collection;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.util.NbtType;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;

public class ArmPlacementPacket implements C2SPacket {

	private Collection<ArmInteractionPoint> points;
	private ListTag receivedTag;
	private BlockPos pos;

	public ArmPlacementPacket(Collection<ArmInteractionPoint> points, BlockPos pos) {
		this.points = points;
		this.pos = pos;
	}

	@Override
	public void read(PacketByteBuf buffer) {
		CompoundTag nbt = buffer.readCompoundTag();
		receivedTag = nbt.getList("Points", NbtType.COMPOUND);
		pos = buffer.readBlockPos();
	}

	@Override
	public void write(PacketByteBuf buffer) {
		CompoundTag nbt = new CompoundTag();
		ListTag pointsNBT = new ListTag();
		points.stream()
			.map(aip -> aip.serialize(pos))
			.forEach(pointsNBT::add);
		nbt.put("Points", pointsNBT);
		buffer.writeCompoundTag(nbt);
		buffer.writeBlockPos(pos);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null)
				return;
			World world = player.world;
			if (world == null || !world.canSetBlock(pos))
				return;
			BlockEntity tileEntity = world.getBlockEntity(pos);
			if (!(tileEntity instanceof ArmBlockEntity))
				return;

			ArmBlockEntity arm = (ArmBlockEntity) tileEntity;
			arm.interactionPointTag = receivedTag;
		});
	}
}
