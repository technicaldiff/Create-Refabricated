package com.simibubi.create.content.contraptions.components.structureMovement.glue;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class GlueEffectPacket implements S2CPacket {

	private BlockPos pos;
	private Direction direction;
	private boolean fullBlock;

	protected GlueEffectPacket() {}

	public GlueEffectPacket(BlockPos pos, Direction direction, boolean fullBlock) {
		this.pos = pos;
		this.direction = direction;
		this.fullBlock = fullBlock;
	}

	public void read(PacketBuffer buffer) {
		pos = buffer.readBlockPos();
		direction = Direction.byIndex(buffer.readByte());
		fullBlock = buffer.readBoolean();
	}

	public void write(PacketBuffer buffer) {
		buffer.writeBlockPos(pos);
		buffer.writeByte(direction.getIndex());
		buffer.writeBoolean(fullBlock);
	}

	@Environment(EnvType.CLIENT)
	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client.execute(() -> {
			Minecraft mc = Minecraft.getInstance();
			if (!mc.player.getBlockPos().withinDistance(pos, 100))
				return;
			SuperGlueItem.spawnParticles(mc.world, pos, direction, fullBlock);
		});
	}

}
