package com.simibubi.create.lib.utility;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

public interface ContainerTypeFactoryWrapper<T extends Container> extends ContainerType.IFactory<T>, ScreenHandlerRegistry.SimpleClientHandlerFactory<T> {
	T create(int windowId, PlayerInventory inv, PacketBuffer data);

	@Override
	default T create(int syncId, PlayerInventory playerInventory) {
		return create(syncId, playerInventory, null);
	}
}
