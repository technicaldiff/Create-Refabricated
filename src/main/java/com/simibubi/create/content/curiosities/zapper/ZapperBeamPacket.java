package com.simibubi.create.content.curiosities.zapper;

import com.simibubi.create.CreateClient;
import com.simibubi.create.content.curiosities.zapper.ZapperRenderHandler.LaserBeam;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class ZapperBeamPacket extends ShootGadgetPacket {

	public Vector3d target;

	public ZapperBeamPacket(Vector3d start, Vector3d target, Hand hand, boolean self) {
		super(start, hand, self);
		this.target = target;
	}

	public void read(PacketBuffer buffer) {
		super.read(buffer);
	}

	@Override
	protected void readAdditional(PacketBuffer buffer) {
		target = new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

	@Override
	protected void writeAdditional(PacketBuffer buffer) {
		buffer.writeDouble(target.x);
		buffer.writeDouble(target.y);
		buffer.writeDouble(target.z);
	}

	@Override
	@Environment(EnvType.CLIENT)
	protected ShootableGadgetRenderHandler getHandler() {
		return CreateClient.ZAPPER_RENDER_HANDLER;
	}

	@Override
	@Environment(EnvType.CLIENT)
	protected void handleAdditional() {
		CreateClient.ZAPPER_RENDER_HANDLER.addBeam(new LaserBeam(location, target));
	}

}
