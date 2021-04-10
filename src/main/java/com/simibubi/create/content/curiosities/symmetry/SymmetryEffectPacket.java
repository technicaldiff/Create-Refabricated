package com.simibubi.create.content.curiosities.symmetry;

import java.util.ArrayList;
import java.util.List;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class SymmetryEffectPacket implements S2CPacket {

	private BlockPos mirror;
	private List<BlockPos> positions;

	protected SymmetryEffectPacket() {}

	public SymmetryEffectPacket(BlockPos mirror, List<BlockPos> positions) {
		this.mirror = mirror;
		this.positions = positions;
	}

	public void read(PacketBuffer buffer) {
		mirror = buffer.readBlockPos();
		int amt = buffer.readInt();
		positions = new ArrayList<>(amt);
		for (int i = 0; i < amt; i++) {
			positions.add(buffer.readBlockPos());
		}
	}

	public void write(PacketBuffer buffer) {
		buffer.writeBlockPos(mirror);
		buffer.writeInt(positions.size());
		for (BlockPos blockPos : positions) {
			buffer.writeBlockPos(blockPos);
		}
	}

	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client.execute(() -> {
			if (Minecraft.getInstance().player.getPositionVec().distanceTo(Vector3d.of(mirror)) > 100)
				return;
			for (BlockPos to : positions)
				SymmetryHandler.drawEffect(mirror, to);
		});
	}

}
