package com.simibubi.create.content.contraptions.components.structureMovement.train.capability;

import com.simibubi.create.lib.utility.LazyOptional;
import com.simibubi.create.lib.utility.MinecartAndRailUtil;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class MinecartControllerUpdatePacket implements S2CPacket {

	int entityID;
	CompoundNBT nbt;

	protected MinecartControllerUpdatePacket() {}

	public MinecartControllerUpdatePacket(MinecartController controller) {
		entityID = controller.cart()
			.getEntityId();
		nbt = controller.create$serializeNBT();
	}

	public void read(PacketBuffer buffer) {
		entityID = buffer.readInt();
		nbt = buffer.readCompoundTag();
	}

	@Override
	public void write(PacketBuffer buffer) {
 		buffer.writeInt(entityID);
		buffer.writeCompoundTag(nbt);
	}

	@Override
	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client
			.execute(this::handleCL);
	}

	@Environment(EnvType.CLIENT)
	private void handleCL() {
		ClientWorld world = Minecraft.getInstance().world;
		if (world == null)
			return;
		Entity entityByID = world.getEntityByID(entityID);
		if (entityByID == null)
			return;
		LazyOptional.ofObject(MinecartAndRailUtil.getController((AbstractMinecartEntity) entityByID))
			.ifPresent(mc -> ((MinecartController) mc).create$deserializeNBT(nbt));

	}

}
