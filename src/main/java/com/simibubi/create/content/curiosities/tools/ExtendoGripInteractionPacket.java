package com.simibubi.create.content.curiosities.tools;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class ExtendoGripInteractionPacket implements C2SPacket {

	private Hand interactionHand;
	private int target;
	private Vector3d specificPoint;

	protected ExtendoGripInteractionPacket() {}

	public ExtendoGripInteractionPacket(Entity target) {
		this(target, null);
	}

	public ExtendoGripInteractionPacket(Entity target, Hand hand) {
		this(target, hand, null);
	}

	public ExtendoGripInteractionPacket(Entity target, Hand hand, Vector3d specificPoint) {
		interactionHand = hand;
		this.specificPoint = specificPoint;
		this.target = target.getEntityId();
	}

	public void read(PacketBuffer buffer) {
		target = buffer.readInt();
		int handId = buffer.readInt();
		interactionHand = handId == -1 ? null : Hand.values()[handId];
		if (buffer.readBoolean())
			specificPoint = new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeInt(target);
		buffer.writeInt(interactionHand == null ? -1 : interactionHand.ordinal());
		buffer.writeBoolean(specificPoint != null);
		if (specificPoint != null) {
			buffer.writeDouble(specificPoint.x);
			buffer.writeDouble(specificPoint.y);
			buffer.writeDouble(specificPoint.z);
		}
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity sender, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server.execute(() -> {
			if (sender == null)
				return;
			Entity entityByID = sender.getServerWorld().getEntityByID(target);
			if (entityByID != null && ExtendoGripItem.isHoldingExtendoGrip(sender)) {
				double d = sender.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
				if (!sender.canEntityBeSeen(entityByID))
					d -= 3;
				d *= d;
				if (sender.getDistanceSq(entityByID) > d) {
					// TODO log?
					return;
				}
				if (interactionHand == null)
					sender.attackTargetEntityWithCurrentItem(entityByID);
				else if (specificPoint == null)
					sender.interactOn(entityByID, interactionHand);
				else
					entityByID.applyPlayerInteraction(sender, specificPoint, interactionHand);
			}
		});
	}

}
