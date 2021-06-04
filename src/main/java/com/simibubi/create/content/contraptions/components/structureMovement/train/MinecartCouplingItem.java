package com.simibubi.create.content.contraptions.components.structureMovement.train;

import org.jetbrains.annotations.Nullable;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.structureMovement.train.capability.MinecartController;
import com.simibubi.create.lib.utility.LazyOptional;
import com.simibubi.create.lib.utility.MinecartAndRailUtil;
import com.tterrag.registrate.fabric.EnvExecutor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class MinecartCouplingItem extends Item {

	public MinecartCouplingItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	public static ActionResultType handleInteractionWithMinecart(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityRayTraceResult hitResult) {
//		Entity interacted = event.getTarget();
		if (!(entity instanceof AbstractMinecartEntity))
			return ActionResultType.PASS;
		AbstractMinecartEntity minecart = (AbstractMinecartEntity) entity;
//		PlayerEntity player = event.getPlayer();
		if (player == null)
			return ActionResultType.PASS;
		LazyOptional<MinecartController> capability = LazyOptional.ofObject((MinecartController) MinecartAndRailUtil.getController(minecart));

		if (!capability.isPresent())
			return ActionResultType.PASS;
		MinecartController controller = capability.orElse(null);

		ItemStack heldItem = player.getHeldItem(hand);
		if (AllItems.MINECART_COUPLING.isIn(heldItem)) {
			if (!onCouplingInteractOnMinecart(player.world, minecart, player, controller))
				return ActionResultType.PASS;
		} else if (AllItems.WRENCH.isIn(heldItem)) {
			if (!onWrenchInteractOnMinecart(player.world, minecart, player, controller))
				return ActionResultType.PASS;
		} else
			return ActionResultType.PASS;

		return ActionResultType.SUCCESS;
	}

	protected static boolean onCouplingInteractOnMinecart(World world,
		AbstractMinecartEntity minecart, PlayerEntity player, MinecartController controller) {
//		World world = event.getWorld();
		if (controller.isFullyCoupled()) {
			if (!world.isRemote)
				CouplingHandler.status(player, "two_couplings_max");
			return true;
		}
		if (world != null && world.isRemote)
			EnvExecutor.runWhenOn(EnvType.CLIENT, () -> () -> cartClicked(player, minecart));
		return true;
	}

	private static boolean onWrenchInteractOnMinecart(World world, AbstractMinecartEntity minecart,
		PlayerEntity player, MinecartController controller) {
		int couplings = (controller.isConnectedToCoupling() ? 1 : 0) + (controller.isLeadingCoupling() ? 1 : 0);
		if (couplings == 0)
			return false;
		if (world.isRemote)
			return true;

		for (boolean forward : Iterate.trueAndFalse) {
			if (controller.hasContraptionCoupling(forward))
				couplings--;
		}

		CouplingHandler.status(player, "removed");
		controller.decouple();
		if (!player.isCreative())
			player.inventory.placeItemBackInInventory(world,
				new ItemStack(AllItems.MINECART_COUPLING.get(), couplings));
		return true;
	}

	@Environment(EnvType.CLIENT)
	private static void cartClicked(PlayerEntity player, AbstractMinecartEntity interacted) {
		CouplingHandlerClient.onCartClicked(player, (AbstractMinecartEntity) interacted);
	}

}
