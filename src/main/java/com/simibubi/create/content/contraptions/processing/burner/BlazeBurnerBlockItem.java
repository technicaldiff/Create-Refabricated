package com.simibubi.create.content.contraptions.processing.burner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.utility.VecHelper;

import com.simibubi.create.lib.annotation.MethodsReturnNonnullByDefault;

import com.simibubi.create.lib.helper.AbstractSpawnerHelper;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlazeBurnerBlockItem extends BlockItem {

	private final boolean capturedBlaze;

	public static BlazeBurnerBlockItem empty(Properties properties) {
		return new BlazeBurnerBlockItem(AllBlocks.BLAZE_BURNER.get(), properties, false);
	}

	public static BlazeBurnerBlockItem withBlaze(Block block, Properties properties) {
		return new BlazeBurnerBlockItem(block, properties, true);
	}

	@Override
	public void addToBlockToItemMap(Map<Block, Item> p_195946_1_, Item p_195946_2_) {
		if (!hasCapturedBlaze())
			return;
		super.addToBlockToItemMap(p_195946_1_, p_195946_2_);
	}

	private BlazeBurnerBlockItem(Block block, Properties properties, boolean capturedBlaze) {
		super(block, properties);
		this.capturedBlaze = capturedBlaze;
	}

	@Override
	public void fillItemGroup(ItemGroup p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
		if (!hasCapturedBlaze())
			return;
		super.fillItemGroup(p_150895_1_, p_150895_2_);
	}

	@Override
	public String getTranslationKey() {
		return hasCapturedBlaze() ? super.getTranslationKey() : "item.create." + Registry.ITEM.getKey(this).getPath();
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (hasCapturedBlaze())
			return super.onItemUse(context);

		World world = context.getWorld();
		BlockPos pos = context.getPos();
		TileEntity te = world.getTileEntity(pos);
		PlayerEntity player = context.getPlayer();

		if (!(te instanceof MobSpawnerTileEntity))
			return super.onItemUse(context);

		AbstractSpawner spawner = ((MobSpawnerTileEntity) te).getSpawnerBaseLogic();
		List<WeightedSpawnerEntity> possibleSpawns = AbstractSpawnerHelper.getPotentialSpawns(spawner);
		if (possibleSpawns.isEmpty()) {
			possibleSpawns = new ArrayList<>();
			possibleSpawns
				.add(AbstractSpawnerHelper.getSpawnData(spawner));
		}

		ResourceLocation blazeId = Registry.ENTITY_TYPE.getKey(EntityType.BLAZE);
		for (WeightedSpawnerEntity e : possibleSpawns) {
			ResourceLocation spawnerEntityId = new ResourceLocation(e.getNbt()
				.getString("id"));
			if (!spawnerEntityId.equals(blazeId))
				continue;

			spawnCaptureEffects(world, VecHelper.getCenterOf(pos));
			if (world.isRemote || player == null)
				return ActionResultType.SUCCESS;

			giveBurnerItemTo(player, context.getItem(), context.getHand());
			return ActionResultType.SUCCESS;
		}

		return super.onItemUse(context);
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack heldItem, PlayerEntity player, LivingEntity entity, Hand hand) {
		if (hasCapturedBlaze())
			return ActionResultType.PASS;
		if (!(entity instanceof BlazeEntity))
			return ActionResultType.PASS;

		World world = player.world;
		spawnCaptureEffects(world, entity.getPositionVec());
		if (world.isRemote)
			return ActionResultType.FAIL;

		giveBurnerItemTo(player, heldItem, hand);
		entity.remove();
		return ActionResultType.FAIL;
	}

	protected void giveBurnerItemTo(PlayerEntity player, ItemStack heldItem, Hand hand) {
		ItemStack filled = AllBlocks.BLAZE_BURNER.asStack();
		if (!player.isCreative())
			heldItem.shrink(1);
		if (heldItem.isEmpty()) {
			player.setHeldItem(hand, filled);
			return;
		}
		player.inventory.placeItemBackInInventory(player.world, filled);
	}

	private void spawnCaptureEffects(World world, Vector3d vec) {
		if (world.isRemote) {
			for (int i = 0; i < 40; i++) {
				Vector3d motion = VecHelper.offsetRandomly(Vector3d.ZERO, world.rand, .125f);
				world.addParticle(ParticleTypes.FLAME, vec.x, vec.y, vec.z, motion.x, motion.y, motion.z);
				Vector3d circle = motion.mul(1, 0, 1)
					.normalize()
					.scale(.5f);
				world.addParticle(ParticleTypes.SMOKE, circle.x, vec.y, circle.z, 0, -0.125, 0);
			}
			return;
		}

		BlockPos soundPos = new BlockPos(vec);
		world.playSound(null, soundPos, SoundEvents.ENTITY_BLAZE_HURT, SoundCategory.HOSTILE, .25f, .75f);
		world.playSound(null, soundPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.HOSTILE, .5f, .75f);
	}

	public boolean hasCapturedBlaze() {
		return capturedBlaze;
	}

}
