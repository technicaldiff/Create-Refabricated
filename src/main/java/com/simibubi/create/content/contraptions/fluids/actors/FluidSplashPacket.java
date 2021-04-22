package com.simibubi.create.content.contraptions.fluids.actors;

import com.simibubi.create.content.contraptions.fluids.FluidFX;

import com.simibubi.create.lib.lba.FluidStack;

import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class FluidSplashPacket implements S2CPacket {

	private BlockPos pos;
	private FluidStack fluid;

	protected FluidSplashPacket() {}

	public FluidSplashPacket(BlockPos pos, FluidStack fluid) {
		this.pos = pos;
		this.fluid = fluid;
	}

	public void read(PacketBuffer buffer) {
		pos = buffer.readBlockPos();
		fluid = buffer.readFluidStack();
	}

	public void write(PacketBuffer buffer) {
		buffer.writeBlockPos(pos);
		buffer.writeFluidStack(fluid);
	}

	public void handle(Minecraft client, ClientPlayNetHandler handler, ResponseTarget responseTarget) {
		client
			.execute(() -> {
				if (Minecraft.getInstance().player.getPositionVec()
					.distanceTo(new Vector3d(pos.getX(), pos.getY(), pos.getZ())) > 100)
					return;
				FluidFX.splash(pos, fluid);
			});
	}

}
