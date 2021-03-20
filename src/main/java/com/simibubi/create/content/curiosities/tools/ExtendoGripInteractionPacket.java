package com.simibubi.create.content.curiosities.tools;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class ExtendoGripInteractionPacket implements C2SPacket {

	private Hand interactionHand;
	private int target;
	private Vec3d specificPoint;

	public ExtendoGripInteractionPacket() { }

	public ExtendoGripInteractionPacket(Entity target) {
		this(target, null);
	}

	public ExtendoGripInteractionPacket(Entity target, Hand hand) {
		this(target, hand, null);
	}

	public ExtendoGripInteractionPacket(Entity target, Hand hand, Vec3d specificPoint) {
		interactionHand = hand;
		this.specificPoint = specificPoint;
		this.target = target.getEntityId();
	}

	@Override
	public void read(PacketByteBuf buffer) {
		target = buffer.readInt();
		int handId = buffer.readInt();
		interactionHand = handId == -1 ? null : Hand.values()[handId];
		if (buffer.readBoolean())
			specificPoint = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

	@Override
	public void write(PacketByteBuf buffer) {
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
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null)
				return;
			Entity entityByID = player.getServerWorld().getEntityById(target);
			if (entityByID != null && ExtendoGripItem.isHoldingExtendoGrip(player)) {
				double d = 5/*player.getAttributeInstance(ForgeMod.REACH_DISTANCE.get()).getValue()*/;
				if (!player.canSee(entityByID))
					d -= 3;
				d *= d;
				if (player.squaredDistanceTo(entityByID) > d) {
					// TODO log?
					return;
				}
				if (interactionHand == null)
					player.attack(entityByID);
				else if (specificPoint == null)
					player.interact(entityByID, interactionHand);
				else
					entityByID.interactAt(player, specificPoint, interactionHand);
			}
		});
	}

}
