package com.simibubi.create.content.contraptions.components.structureMovement.sync;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class ContraptionInteractionPacket implements C2SPacket {

	private Hand interactionHand;
	private int target;
	private BlockPos localPos;
	private Direction face;

	protected ContraptionInteractionPacket() {}

	public ContraptionInteractionPacket(AbstractContraptionEntity target, Hand hand, BlockPos localPos, Direction side) {
		this.interactionHand = hand;
		this.localPos = localPos;
		this.target = target.getEntityId();
		this.face = side;
	}

	public void read(PacketBuffer buffer) {
		target = buffer.readInt();
		int handId = buffer.readInt();
		interactionHand = handId == -1 ? null : Hand.values()[handId];
		localPos = buffer.readBlockPos();
		face = Direction.byIndex(buffer.readShort());
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeInt(target);
		buffer.writeInt(interactionHand == null ? -1 : interactionHand.ordinal());
		buffer.writeBlockPos(localPos);
		buffer.writeShort(face.getIndex());
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity sender, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server.execute(() -> {
			if (sender == null)
				return;
			Entity entityByID = sender.getServerWorld().getEntityByID(target);
			if (!(entityByID instanceof AbstractContraptionEntity))
				return;
			AbstractContraptionEntity contraptionEntity = (AbstractContraptionEntity) entityByID;
			double d = sender.getAttribute(ReachEntityAttributes.REACH).getValue() + 10;
			if (!sender.canEntityBeSeen(entityByID))
				d -= 3;
			d *= d;
			if (sender.getDistanceSq(entityByID) > d) {
				// TODO log?
				return;
			}
			if (contraptionEntity.handlePlayerInteraction(sender, localPos, face, interactionHand))
				sender.swingHand(interactionHand, true);
		});
	}

}
