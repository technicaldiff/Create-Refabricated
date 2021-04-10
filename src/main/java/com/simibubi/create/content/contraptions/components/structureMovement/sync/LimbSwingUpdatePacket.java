package com.simibubi.create.content.contraptions.components.structureMovement.sync;

import com.simibubi.create.lib.helper.EntityHelper;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;

public class LimbSwingUpdatePacket implements S2CPacket {

	private int entityId;
	private Vector3d position;
	private float limbSwing;

	protected LimbSwingUpdatePacket() {}

	public LimbSwingUpdatePacket(int entityId, Vector3d position, float limbSwing) {
		this.entityId = entityId;
		this.position = position;
		this.limbSwing = limbSwing;
	}

	public void read(PacketBuffer buffer) {
		entityId = buffer.readInt();
		position = new Vector3d(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
		limbSwing = buffer.readFloat();
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeInt(entityId);
		buffer.writeFloat((float) position.x);
		buffer.writeFloat((float) position.y);
		buffer.writeFloat((float) position.z);
		buffer.writeFloat(limbSwing);
	}

	@Override
	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client
			.execute(() -> {
				ClientWorld world = Minecraft.getInstance().world;
				if (world == null)
					return;
				Entity entity = world.getEntityByID(entityId);
				if (entity == null)
					return;
				CompoundNBT data = EntityHelper.getExtraCustomData(entity);
				data.putInt("LastOverrideLimbSwingUpdate", 0);
				data.putFloat("OverrideLimbSwing", limbSwing);
				entity.setPositionAndRotationDirect(position.x, position.y, position.z, entity.rotationYaw,
					entity.rotationPitch, 2, false);
			});
	}

}
