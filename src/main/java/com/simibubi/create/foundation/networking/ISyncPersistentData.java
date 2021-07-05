package com.simibubi.create.foundation.networking;

import java.util.Iterator;

import com.simibubi.create.lib.helper.EntityHelper;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public interface ISyncPersistentData {

	void onPersistentDataUpdated();

	default void syncPersistentDataWithTracking(Entity self) {
		AllPackets.channel.sendToClientsTracking(new Packet(self), self);
	}

	public static class Packet implements S2CPacket {

		private int entityId;
		private Entity entity;
		private CompoundNBT readData;

		protected Packet() {}

		public Packet(Entity entity) {
			this.entity = entity;
			this.entityId = entity.getEntityId();
		}

		@Override
		public void read(PacketBuffer buffer) {
			entityId = buffer.readInt();
			readData = buffer.readCompoundTag();
		}

		@Override
		public void write(PacketBuffer buffer) {
			buffer.writeInt(entityId);
			buffer.writeCompoundTag(EntityHelper.getExtraCustomData(entity));
		}

		@Override
		public void handle(Minecraft client, ClientPlayNetHandler handler, SimpleChannel.ResponseTarget responseTarget) {
			client
					.execute(() -> {
						Entity entityByID = Minecraft.getInstance().world.getEntityByID(entityId);
						if (!(entityByID instanceof ISyncPersistentData))
							return;
						CompoundNBT data = EntityHelper.getExtraCustomData(entityByID);
						for (Iterator<String> iterator = data.keySet()
								.iterator(); iterator.hasNext(); ) {
							data.remove(iterator.next());
						}
						data.merge(readData);
						((ISyncPersistentData) entityByID).onPersistentDataUpdated();
					});
		}
	}

}
