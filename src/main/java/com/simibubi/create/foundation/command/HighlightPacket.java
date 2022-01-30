package com.simibubi.create.foundation.command;

import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.CreateClient;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;

public class HighlightPacket implements S2CPacket {

	private BlockPos pos;

	protected HighlightPacket() {}

	public HighlightPacket(BlockPos pos) {
		this.pos = pos;
	}

	public void read(PacketBuffer buffer) {
		this.pos = BlockPos.fromLong(buffer.readLong());
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeLong(pos.toLong());
	}

	@Override
	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client
			.execute(() -> {
				performHighlight(pos);
			});

	}

	@Environment(EnvType.CLIENT)
	public static void performHighlight(BlockPos pos) {
		if (Minecraft.getInstance().world == null || !Minecraft.getInstance().world.isBlockPresent(pos))
			return;

		CreateClient.OUTLINER.showAABB("highlightCommand", VoxelShapes.fullCube()
				.getBoundingBox()
				.offset(pos), 200)
				.lineWidth(1 / 32f)
				.colored(0xEeEeEe)
				// .colored(0x243B50)
				.withFaceTexture(AllSpecialTextures.SELECTION);

	}
}
