package com.simibubi.create.lib.utility;

import java.util.function.Consumer;

import com.simibubi.create.lib.mixin.accessor.ServerPlayerEntityAccessor;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SOpenWindowPacket;

public class NetworkUtil {
	/**
	 * This is a poorly stitched together mix of forge's NetworkHooks.openGui() and {@link ServerPlayerEntity#openContainer}
	 */
	public static void openGUI(ServerPlayerEntity player, INamedContainerProvider containerProvider, Consumer<PacketBuffer> extraDataWriter) {
		if (player.world.isRemote) return;
		player.closeContainer();
		((ServerPlayerEntityAccessor) player).getNextWindowId();
		int openContainerId = ((ServerPlayerEntityAccessor) player).getCurrentWindowId();
		PacketBuffer extraData = new PacketBuffer(Unpooled.buffer());
		extraDataWriter.accept(extraData);
		extraData.readerIndex(0);

		PacketBuffer output = new PacketBuffer(Unpooled.buffer());
		output.writeVarInt(extraData.readableBytes());
		output.writeBytes(extraData);

		if (output.readableBytes() > 32600 || output.readableBytes() < 1) {
			throw new IllegalArgumentException("Invalid PacketBuffer for openGui, found "+ output.readableBytes()+ " bytes");
		}

		Container c = containerProvider.createMenu(openContainerId, player.inventory, player);
		ContainerType<?> type = c.getType();

		Container container = containerProvider.createMenu(openContainerId, player.inventory, player);
		if (container == null) {
			return;
		}

		player.connection.sendPacket(new SOpenWindowPacket(container.windowId, container.getType(), containerProvider.getDisplayName()));
		container.addListener(player);
		player.openContainer = container;

		player.openContainer = c;
		player.openContainer.addListener(player);
	}
}
