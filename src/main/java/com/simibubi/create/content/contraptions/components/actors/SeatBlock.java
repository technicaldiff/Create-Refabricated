package com.simibubi.create.content.contraptions.components.actors;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.utility.DyeHelper;

import com.simibubi.create.lib.annotation.MethodsReturnNonnullByDefault;
import com.simibubi.create.lib.block.CustomPathNodeTypeBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SeatBlock extends Block implements CustomPathNodeTypeBlock {

	private final boolean inCreativeTab;

	public SeatBlock(Properties p_i48440_1_, boolean inCreativeTab) {
		super(p_i48440_1_);
		this.inCreativeTab = inCreativeTab;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> p_149666_2_) {
		if (group != ItemGroup.SEARCH && !inCreativeTab)
			return;
		super.fillItemGroup(group, p_149666_2_);
	}

	@Override
	public void onFallenUpon(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
		super.onFallenUpon(p_180658_1_, p_180658_2_, p_180658_3_, p_180658_4_ * 0.5F);
	}

	@Override
	public void onLanded(IBlockReader reader, Entity entity) {
		BlockPos pos = entity.getBlockPos();
		if (entity instanceof PlayerEntity || !(entity instanceof LivingEntity) || !canBePickedUp(entity) || isSeatOccupied(entity.world, pos)) {
			Blocks.PINK_BED.onLanded(reader, entity);
			return;
		}
		if (reader.getBlockState(pos)
			.getBlock() != this)
			return;
		sitDown(entity.world, pos, entity);
	}

	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos,
		@Nullable MobEntity entity) {
		return PathNodeType.RAIL;
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
		ISelectionContext p_220053_4_) {
		return AllShapes.SEAT;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_,
		ISelectionContext p_220071_4_) {
		return AllShapes.SEAT_COLLISION;
	}

	@Override
	public ActionResultType onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
		BlockRayTraceResult p_225533_6_) {
		if (player.isSneaking())
			return ActionResultType.PASS;

		ItemStack heldItem = player.getHeldItem(hand);
		for (DyeColor color : DyeColor.values()) {
			if (!heldItem.getItem()
					.isIn(DyeHelper.getTagOfDye(color)))
				continue;
			if (world.isRemote)
				return ActionResultType.SUCCESS;

			BlockState newState = AllBlocks.SEATS.get(color).getDefaultState();
			if (newState != state)
				world.setBlockState(pos, newState);
			return ActionResultType.SUCCESS;
		}

		List<SeatEntity> seats = world.getEntitiesWithinAABB(SeatEntity.class, new AxisAlignedBB(pos));
		if (!seats.isEmpty()) {
			SeatEntity seatEntity = seats.get(0);
			List<Entity> passengers = seatEntity.getPassengers();
			if (!passengers.isEmpty() && passengers.get(0) instanceof PlayerEntity)
				return ActionResultType.PASS;
			if (!world.isRemote) {
				seatEntity.removePassengers();
				player.startRiding(seatEntity);
			}
			return ActionResultType.SUCCESS;
		}

		if (world.isRemote)
			return ActionResultType.SUCCESS;
		sitDown(world, pos, player);
		return ActionResultType.SUCCESS;
	}

	public static boolean isSeatOccupied(World world, BlockPos pos) {
		return !world.getEntitiesWithinAABB(SeatEntity.class, new AxisAlignedBB(pos))
			.isEmpty();
	}

	public static boolean canBePickedUp(Entity passenger) {
		return !(passenger instanceof PlayerEntity) && (passenger instanceof LivingEntity);
	}

	public static void sitDown(World world, BlockPos pos, Entity entity) {
		if (world.isRemote)
			return;
		SeatEntity seat = new SeatEntity(world, pos);
		seat.setPos(pos.getX() + .5f, pos.getY(), pos.getZ() + .5f);
		world.addEntity(seat);
		entity.startRiding(seat, true);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader reader, BlockPos pos, PathType type) {
		return false;
	}

}
