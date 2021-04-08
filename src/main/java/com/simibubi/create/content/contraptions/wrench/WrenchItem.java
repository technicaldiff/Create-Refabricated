package com.simibubi.create.content.contraptions.wrench;

import javax.annotation.Nonnull;

import com.simibubi.create.AllItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

public class WrenchItem extends Item {

	public WrenchItem(Properties properties) {
		super(properties);
	}

	@Nonnull
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		if (player == null || !player.isAllowEdit())
			return super.onItemUse(context);

		BlockState state = context.getWorld()
			.getBlockState(context.getPos());
		if (!(state.getBlock() instanceof IWrenchable))
			return super.onItemUse(context);
		IWrenchable actor = (IWrenchable) state.getBlock();

		if (player.isSneaking())
			return actor.onSneakWrenched(state, context);
		return actor.onWrenched(state, context);
	}
	
	public static void wrenchInstaKillsMinecarts(PlayerEntity player, World world, Hand hand, Entity target, @Nullable EntityRayTraceResult entityRayTraceResult) {
		if (!(target instanceof AbstractMinecartEntity))
			return;
		ItemStack heldItem = player.getHeldItemMainhand();
		if (!AllItems.WRENCH.isIn(heldItem))
			return;
		if (player.isCreative())
			return;
		AbstractMinecartEntity minecart = (AbstractMinecartEntity) target;
		minecart.attackEntityFrom(DamageSource.causePlayerDamage(player), 100);
	}
	
}
