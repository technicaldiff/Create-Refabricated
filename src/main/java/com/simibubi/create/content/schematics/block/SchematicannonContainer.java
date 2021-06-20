package com.simibubi.create.content.schematics.block;

import com.simibubi.create.AllContainerTypes;
import com.simibubi.create.lib.lba.item.SlotItemHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class SchematicannonContainer extends Container {

	private SchematicannonTileEntity te;
	private PlayerEntity player;

	public SchematicannonContainer(ContainerType<?> type, int id, PlayerInventory inv, PacketBuffer buffer) {
		super(type, id);
		player = inv.player;
		ClientWorld world = Minecraft.getInstance().world;
		TileEntity tileEntity = world.getTileEntity(buffer.readBlockPos());
		if (tileEntity instanceof SchematicannonTileEntity) {
			this.te = (SchematicannonTileEntity) tileEntity;
			this.te.handleUpdateTag(te.getBlockState(), buffer.readCompoundTag());
			init();
		}
	}

	public SchematicannonContainer(ContainerType<?> type, int id, PlayerInventory inv, SchematicannonTileEntity te) {
		super(type, id);
		player = inv.player;
		this.te = te;
		init();
	}

	public static SchematicannonContainer create(int id, PlayerInventory inv, SchematicannonTileEntity te) {
		return new SchematicannonContainer(AllContainerTypes.SCHEMATICANNON.get(), id, inv, te);
	}

	protected void init() {
		int x = 0;
		int y = 0;

		addSlot(SlotItemHandler.create(te.inventory, 0, x + 15, y + 65));
		addSlot(SlotItemHandler.create(te.inventory, 1, x + 171, y + 65));
		addSlot(SlotItemHandler.create(te.inventory, 2, x + 134, y + 19));
		addSlot(SlotItemHandler.create(te.inventory, 3, x + 174, y + 19));
		addSlot(SlotItemHandler.create(te.inventory, 4, x + 15, y + 19));

		// player Slots
		for (int row = 0; row < 3; ++row)
			for (int col = 0; col < 9; ++col)
				addSlot(new Slot(player.inventory, col + row * 9 + 9, 37 + col * 18, 161 + row * 18));
		for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot)
			addSlot(new Slot(player.inventory, hotbarSlot, 37 + hotbarSlot * 18, 219));

		detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
	}

	public SchematicannonTileEntity getTileEntity() {
		return te;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		Slot clickedSlot = getSlot(index);
		if (!clickedSlot.getHasStack())
			return ItemStack.EMPTY;
		ItemStack stack = clickedSlot.getStack();

		if (index < 5) {
			mergeItemStack(stack, 5, inventorySlots.size(), false);
		} else {
			if (mergeItemStack(stack, 0, 1, false) || mergeItemStack(stack, 2, 3, false)
					|| mergeItemStack(stack, 4, 5, false))
				;
		}

		return ItemStack.EMPTY;
	}

}
