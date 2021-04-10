package com.simibubi.create.content.logistics.block.mechanicalArm;

import java.util.Collection;

import com.simibubi.create.lib.utility.Constants.NBT;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArmPlacementPacket implements C2SPacket {

	private Collection<ArmInteractionPoint> points;
	private ListNBT receivedTag;
	private BlockPos pos;

	protected ArmPlacementPacket() {}

	public ArmPlacementPacket(Collection<ArmInteractionPoint> points, BlockPos pos) {
		this.points = points;
		this.pos = pos;
	}

	public void read(PacketBuffer buffer) {
		CompoundNBT nbt = buffer.readCompoundTag();
		receivedTag = nbt.getList("Points", NBT.TAG_COMPOUND);
		pos = buffer.readBlockPos();
	}

	@Override
	public void write(PacketBuffer buffer) {
		CompoundNBT nbt = new CompoundNBT();
		ListNBT pointsNBT = new ListNBT();
		points.stream()
			.map(aip -> aip.serialize(pos))
			.forEach(pointsNBT::add);
		nbt.put("Points", pointsNBT);
		buffer.writeCompoundTag(nbt);
		buffer.writeBlockPos(pos);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server
			.execute(() -> {
				if (player == null)
					return;
				World world = player.world;
				if (world == null || !world.isBlockPresent(pos))
					return;
				TileEntity tileEntity = world.getTileEntity(pos);
				if (!(tileEntity instanceof ArmTileEntity))
					return;

				ArmTileEntity arm = (ArmTileEntity) tileEntity;
				arm.interactionPointTag = receivedTag;
			});

	}

}
