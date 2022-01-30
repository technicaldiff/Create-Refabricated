package com.simibubi.create.content.contraptions.wrench;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;

import net.minecraft.block.Block;
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

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

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
		Block block = state.getBlock();

		if (!(block instanceof IWrenchable)) {
			if (canWrenchPickup(state))
				return onItemUseOnOther(context);
			return super.onItemUse(context);
		}

		IWrenchable actor = (IWrenchable) block;
		if (player.isSneaking())
			return actor.onSneakWrenched(state, context);
		return actor.onWrenched(state, context);
	}

	private boolean canWrenchPickup(BlockState state) {
		return AllTags.AllBlockTags.WRENCH_PICKUP.matches(state);
	}

	private ActionResultType onItemUseOnOther(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = world.getBlockState(pos);
		if (!(world instanceof ServerWorld))
			return ActionResultType.SUCCESS;
		if (player != null && !player.isCreative())
			Block.getDrops(state, (ServerWorld) world, pos, world.getTileEntity(pos), player, context.getItem())
				.forEach(itemStack -> player.inventory.placeItemBackInInventory(world, itemStack));
		state.spawnAdditionalDrops((ServerWorld) world, pos, ItemStack.EMPTY);
		world.destroyBlock(pos, false);
		AllSoundEvents.WRENCH_REMOVE.playOnServer(world, pos, 1, Create.RANDOM.nextFloat() * .5f + .5f);
		return ActionResultType.SUCCESS;
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
