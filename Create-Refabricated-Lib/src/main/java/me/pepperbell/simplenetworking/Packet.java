package me.pepperbell.simplenetworking;

import net.minecraft.network.PacketBuffer;

public interface Packet {
	void read(PacketBuffer buf);

	void write(PacketBuffer buf);
}
