package com.simibubi.create.content.contraptions.components.structureMovement.train;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.structureMovement.train.capability.CapabilityMinecartController;
import com.simibubi.create.content.contraptions.components.structureMovement.train.capability.MinecartController;
import com.simibubi.create.lib.utility.LazyOptional;
import com.tterrag.registrate.fabric.EnvExecutor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class MinecartCouplingItem extends Item {

	public MinecartCouplingItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void handleInteractionWithMinecart(PlayerInteractEvent.EntityInteract event) {
		Entity interacted = event.getTarget();
		if (!(interacted instanceof AbstractMinecartEntity))
			return;
		AbstractMinecartEntity minecart = (AbstractMinecartEntity) interacted;
		PlayerEntity player = event.getPlayer();
		if (player == null)
			return;
		LazyOptional<MinecartController> capability =
				CapabilityUtil.getCapability(minecart, CapabilityMinecartController.MINECART_CONTROLLER_CAPABILITY);
		if (!capability.isPresent())
			return;
		MinecartController controller = capability.orElse(null);

		ItemStack heldItem = player.getHeldItem(event.getHand());
		if (AllItems.MINECART_COUPLING.isIn(heldItem)) {
			if (!onCouplingInteractOnMinecart(event, minecart, player, controller))
				return;
		} else if (AllItems.WRENCH.isIn(heldItem)) {
			if (!onWrenchInteractOnMinecart(event, minecart, player, controller))
				return;
		} else
			return;

		event.setCanceled(true);
		event.setCancellationResult(ActionResultType.SUCCESS);
	}

	protected static boolean onCouplingInteractOnMinecart(PlayerInteractEvent.EntityInteract event,
		AbstractMinecartEntity minecart, PlayerEntity player, MinecartController controller) {
		World world = event.getWorld();
		if (controller.isFullyCoupled()) {
			if (!world.isRemote)
				CouplingHandler.status(player, "two_couplings_max");
			return true;
		}
		if (world != null && world.isRemote)
			EnvExecutor.runWhenOn(EnvType.CLIENT, () -> () -> cartClicked(player, minecart));
		return true;
	}

	private static boolean onWrenchInteractOnMinecart(EntityInteract event, AbstractMinecartEntity minecart,
		PlayerEntity player, MinecartController controller) {
		int couplings = (controller.isConnectedToCoupling() ? 1 : 0) + (controller.isLeadingCoupling() ? 1 : 0);
		if (couplings == 0)
			return false;
		if (event.getWorld().isRemote)
			return true;

		CouplingHandler.status(player, "removed");
		controller.decouple();
		if (!player.isCreative())
			player.inventory.placeItemBackInInventory(event.getWorld(),
				new ItemStack(AllItems.MINECART_COUPLING.get(), couplings));
		return true;
	}

	@Environment(EnvType.CLIENT)
	private static void cartClicked(PlayerEntity player, AbstractMinecartEntity interacted) {
		CouplingHandlerClient.onCartClicked(player, (AbstractMinecartEntity) interacted);
	}

}
