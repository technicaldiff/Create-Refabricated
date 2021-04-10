package com.simibubi.create.content.contraptions.components.structureMovement.sync;

import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.lib.helper.ServerPlayNetHandlerHelper;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector3d;

public class ClientMotionPacket implements C2SPacket {

	private Vector3d motion;
	private boolean onGround;
	private float limbSwing;

	protected ClientMotionPacket() {}

	public ClientMotionPacket(Vector3d motion, boolean onGround, float limbSwing) {
		this.motion = motion;
		this.onGround = onGround;
		this.limbSwing = limbSwing;
	}

	public void read(PacketBuffer buffer) {
		motion = new Vector3d(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
		onGround = buffer.readBoolean();
		limbSwing = buffer.readFloat();
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeFloat((float) motion.x);
		buffer.writeFloat((float) motion.y);
		buffer.writeFloat((float) motion.z);
		buffer.writeBoolean(onGround);
		buffer.writeFloat(limbSwing);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity sender, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server
			.execute(() -> {
				if (sender == null)
					return;
				sender.setMotion(motion);
				sender.setOnGround(onGround);
				if (onGround) {
					sender.handleFallDamage(sender.fallDistance, 1);
					sender.fallDistance = 0;
					ServerPlayNetHandlerHelper.setFloatingTickCount(sender.connection, 0);
				}
				AllPackets.channel.sendToClientsTracking(new LimbSwingUpdatePacket(sender.getEntityId(), sender.getPositionVec(), limbSwing), sender);
			});
	}

}
