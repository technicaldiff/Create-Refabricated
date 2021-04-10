package com.simibubi.create.content.logistics.block.depot;

import com.simibubi.create.AllBlocks;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EjectorPlacementPacket implements C2SPacket {

	private int h, v;
	private BlockPos pos;
	private Direction facing;

	protected EjectorPlacementPacket() {}

	public EjectorPlacementPacket(int h, int v, BlockPos pos, Direction facing) {
		this.h = h;
		this.v = v;
		this.pos = pos;
		this.facing = facing;
	}

	public void read(PacketBuffer buffer) {
		h = buffer.readInt();
		v = buffer.readInt();
		pos = buffer.readBlockPos();
		facing = Direction.byIndex(buffer.readVarInt());
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeInt(h);
		buffer.writeInt(v);
		buffer.writeBlockPos(pos);
		buffer.writeVarInt(facing.getIndex());
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
				BlockState state = world.getBlockState(pos);
				if (tileEntity instanceof EjectorTileEntity)
					((EjectorTileEntity) tileEntity).setTarget(h, v);
				if (AllBlocks.WEIGHTED_EJECTOR.has(state))
					world.setBlockState(pos, state.with(EjectorBlock.HORIZONTAL_FACING, facing));
			});

	}

}
