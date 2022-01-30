package com.simibubi.create.content.curiosities.zapper;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public abstract class ShootGadgetPacket implements S2CPacket {

	public Vector3d location;
	public Hand hand;
	public boolean self;

	public ShootGadgetPacket(Vector3d location, Hand hand, boolean self) {
		this.location = location;
		this.hand = hand;
		this.self = self;
	}

	public ShootGadgetPacket(PacketBuffer buffer) {
		hand = buffer.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
		self = buffer.readBoolean();
		location = new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
		readAdditional(buffer);
	}

	public final void write(PacketBuffer buffer) {
		buffer.writeBoolean(hand == Hand.MAIN_HAND);
		buffer.writeBoolean(self);
		buffer.writeDouble(location.x);
		buffer.writeDouble(location.y);
		buffer.writeDouble(location.z);
		writeAdditional(buffer);
	}

	protected abstract void readAdditional(PacketBuffer buffer);

	protected abstract void writeAdditional(PacketBuffer buffer);

	@Environment(EnvType.CLIENT)
	protected abstract void handleAdditional();

	@Environment(EnvType.CLIENT)
	protected abstract ShootableGadgetRenderHandler getHandler();

	@Override
	@Environment(EnvType.CLIENT)
	public void handle(Minecraft client, ClientPlayNetHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		client.execute(() -> {
				Entity renderViewEntity = Minecraft.getInstance()
					.getRenderViewEntity();
				if (renderViewEntity == null)
					return;
				if (renderViewEntity.getPositionVec()
					.distanceTo(location) > 100)
					return;

				ShootableGadgetRenderHandler renderHandler = getHandler();
				handleAdditional();
				if (self)
					renderHandler.shoot(hand, location);
				else
					renderHandler.playSound(hand, location);
			});
	}

}
