package me.pepperbell.simplenetworking;

import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;

public interface C2SPacket extends Packet {
	/**
	 * This method will be run on the network thread. Most method calls should be performed on the server thread by wrapping the code in a lambda:
	 * <pre>
	 * <code>server.execute(() -> {
	 * 	// code here
	 * }</code></pre>
	 */
	void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, ResponseTarget responseTarget);
}
