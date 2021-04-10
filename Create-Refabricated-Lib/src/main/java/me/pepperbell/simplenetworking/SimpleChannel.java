package me.pepperbell.simplenetworking;

import java.lang.reflect.Constructor;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.server.ServerWorld;

public class SimpleChannel {
	private static final Logger LOGGER = LogManager.getLogger("Simple Networking API");

	private final ResourceLocation channelName;
	private final BiMap<Integer, Class<?>> c2sIdMap = HashBiMap.create();
	private final BiMap<Integer, Class<?>> s2cIdMap = HashBiMap.create();
	private C2SHandler c2sHandler;
	private S2CHandler s2cHandler;

	public SimpleChannel(ResourceLocation channelName) {
		this.channelName = channelName;
	}

	public void initServerListener() {
		c2sHandler = new C2SHandler();
		ServerPlayNetworking.registerGlobalReceiver(channelName, c2sHandler);
	}

	@Environment(EnvType.CLIENT)
	public void initClientListener() {
		s2cHandler = new S2CHandler();
		ClientPlayNetworking.registerGlobalReceiver(channelName, s2cHandler);
	}

	/**
	 * The registered class <b>must</b> have a nullary constructor or else an error will be thrown.
	 *
	 * <p>A nullary constructor is one that has no arguments and in this case should not do anything.
	 * For example, if the class name is {@code ExamplePacket}, the public nullary constructor would be <code>public ExamplePacket() {}</code>.
	 * The visibility of this constructor does not matter for this method.
	 */
	public <T extends C2SPacket> void registerC2SPacket(Class<T> clazz, int id) {
		c2sIdMap.put(id, clazz);
	}

	/**
	 * The registered class <b>must</b> have a nullary constructor or else an error will be thrown.
	 *
	 * <p>A nullary constructor is one that has no arguments and in this case should not do anything.
	 * For example, if the class name is {@code ExamplePacket}, the public nullary constructor would be <code>public ExamplePacket() {}</code>.
	 * The visibility of this constructor does not matter for this method.
	 */
	public <T extends S2CPacket> void registerS2CPacket(Class<T> clazz, int id) {
		s2cIdMap.put(id, clazz);
	}

	private PacketBuffer createBuf(C2SPacket packet) {
		Integer id = c2sIdMap.inverse().get(packet.getClass());
		if (id == null) {
			LOGGER.error("Could not get id for c2s packet " + packet.toString() + " in channel " + channelName);
			return null;
		}
		PacketBuffer buf = PacketByteBufs.create();
		buf.writeVarInt(id);
		packet.write(buf);
		return buf;
	}

	private PacketBuffer createBuf(S2CPacket packet) {
		Integer id = s2cIdMap.inverse().get(packet.getClass());
		if (id == null) {
			LOGGER.error("Could not get id for s2c packet " + packet.toString() + " in channel " + channelName);
			return null;
		}
		PacketBuffer buf = PacketByteBufs.create();
		buf.writeVarInt(id);
		packet.write(buf);
		return buf;
	}

	@Environment(EnvType.CLIENT)
	public void sendToServer(C2SPacket packet) {
		PacketBuffer buf = createBuf(packet);
		if (buf == null) return;
		ClientPlayNetworking.send(channelName, buf);
	}

	public void sendToClient(S2CPacket packet, ServerPlayerEntity player) {
		PacketBuffer buf = createBuf(packet);
		if (buf == null) return;
		ServerPlayNetworking.send(player, channelName, buf);
	}

	public void sendToClients(S2CPacket packet, Collection<ServerPlayerEntity> players) {
		PacketBuffer buf = createBuf(packet);
		if (buf == null) return;
		for (ServerPlayerEntity player : players) {
			ServerPlayNetworking.send(player, channelName, buf);
		}
	}

	public void sendToClientsInServer(S2CPacket packet, MinecraftServer server) {
		sendToClients(packet, PlayerLookup.all(server));
	}

	public void sendToClientsInWorld(S2CPacket packet, ServerWorld world) {
		sendToClients(packet, PlayerLookup.world(world));
	}

	public void sendToClientsTracking(S2CPacket packet, ServerWorld world, BlockPos pos) {
		sendToClients(packet, PlayerLookup.tracking(world, pos));
	}

	public void sendToClientsTracking(S2CPacket packet, ServerWorld world, ChunkPos pos) {
		sendToClients(packet, PlayerLookup.tracking(world, pos));
	}

	public void sendToClientsTracking(S2CPacket packet, Entity entity) {
		sendToClients(packet, PlayerLookup.tracking(entity));
	}

	public void sendToClientsTracking(S2CPacket packet, TileEntity blockEntity) {
		sendToClients(packet, PlayerLookup.tracking(blockEntity));
	}

	public void sendToClientsAround(S2CPacket packet, ServerWorld world, Vector3d pos, double radius) {
		sendToClients(packet, PlayerLookup.around(world, pos, radius));
	}

	public void sendToClientsAround(S2CPacket packet, ServerWorld world, Vector3i pos, double radius) {
		sendToClients(packet, PlayerLookup.around(world, pos, radius));
	}

	@Environment(EnvType.CLIENT)
	public void sendResponseToServer(ResponseTarget target, C2SPacket packet) {
		PacketBuffer buf = createBuf(packet);
		if (buf == null) return;
		target.sender.sendPacket(channelName, buf);
	}

	public void sendResponseToClient(ResponseTarget target, S2CPacket packet) {
		PacketBuffer buf = createBuf(packet);
		if (buf == null) return;
		target.sender.sendPacket(channelName, buf);
	}

	public static class ResponseTarget {
		private final PacketSender sender;

		private ResponseTarget(PacketSender sender) {
			this.sender = sender;
		}
	}

	private class C2SHandler implements ServerPlayNetworking.PlayChannelHandler {
		@Override
		public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, PacketBuffer buf, PacketSender responseSender) {
			int id = buf.readVarInt();
			C2SPacket packet = null;
			try {
				Class<?> clazz = c2sIdMap.get(id);
				Constructor<?> ctor = clazz.getDeclaredConstructor();
				ctor.setAccessible(true);
				packet = (C2SPacket) ctor.newInstance();
			} catch (Exception e) {
				LOGGER.error("Could not create c2s packet in channel " + channelName + " with id " + id, e);
			}
			if (packet != null) {
				packet.read(buf);
				packet.handle(server, player, handler, new ResponseTarget(responseSender));
			}
		}
	}

	@Environment(EnvType.CLIENT)
	private class S2CHandler implements ClientPlayNetworking.PlayChannelHandler {
		@Override
		public void receive(Minecraft client, ClientPlayNetHandler handler, PacketBuffer buf, PacketSender responseSender) {
			int id = buf.readVarInt();
			S2CPacket packet = null;
			try {
				Class<?> clazz = s2cIdMap.get(id);
				Constructor<?> ctor = clazz.getDeclaredConstructor();
				ctor.setAccessible(true);
				packet = (S2CPacket) ctor.newInstance();
			} catch (Exception e) {
				LOGGER.error("Could not create s2c packet in channel " + channelName + " with id " + id, e);
			}
			if (packet != null) {
				packet.read(buf);
				packet.handle(client, handler, new ResponseTarget(responseSender));
			}
		}
	}
}
