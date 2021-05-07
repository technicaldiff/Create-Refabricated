package com.simibubi.create.content.curiosities.zapper;

import java.util.Objects;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.AllTags.AllBlockTags;
import com.simibubi.create.foundation.utility.BlockHelper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ZapperInteractionHandler {

	public static ActionResultType leftClickingBlocksWithTheZapperSelectsTheBlock(PlayerEntity player, World world, Hand hand, BlockPos blockPos, Direction direction) {
		if (world.isRemote)
			return ActionResultType.PASS;
		ItemStack heldItem = player
			.getHeldItemMainhand();
		if (heldItem.getItem() instanceof ZapperItem && trySelect(heldItem, player)) {
			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	public static boolean trySelect(ItemStack stack, PlayerEntity player) {
		if (player.isSneaking())
			return false;

		Vector3d start = player.getPositionVec()
			.add(0, player.getEyeHeight(), 0);
		Vector3d range = player.getLookVec()
			.scale(getRange(stack));
		BlockRayTraceResult raytrace = player.world
			.rayTraceBlocks(new RayTraceContext(start, start.add(range), BlockMode.OUTLINE, FluidMode.NONE, player));
		BlockPos pos = raytrace.getPos();
		if (pos == null)
			return false;

		player.world.sendBlockBreakProgress(player.getEntityId(), pos, -1);
		BlockState newState = player.world.getBlockState(pos);

		if (BlockHelper.getRequiredItem(newState)
			.isEmpty())
			return false;
		if (newState.getBlock().hasBlockEntity() && !AllBlockTags.SAFE_NBT.matches(newState))
			return false;
		if (newState.contains(BlockStateProperties.DOUBLE_BLOCK_HALF))
			return false;
		if (newState.contains(BlockStateProperties.ATTACHED))
			return false;
		if (newState.contains(BlockStateProperties.HANGING))
			return false;
		if (newState.contains(BlockStateProperties.BED_PART))
			return false;
		if (newState.contains(BlockStateProperties.STAIRS_SHAPE))
			newState = newState.with(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT);
		if (newState.contains(BlockStateProperties.PERSISTENT))
			newState = newState.with(BlockStateProperties.PERSISTENT, true);
		if (newState.contains(BlockStateProperties.WATERLOGGED))
			newState = newState.with(BlockStateProperties.WATERLOGGED, false);

		CompoundNBT data = null;
		TileEntity tile = player.world.getTileEntity(pos);
		if (tile != null) {
			data = tile.write(new CompoundNBT());
			data.remove("x");
			data.remove("y");
			data.remove("z");
			data.remove("id");
		}
		CompoundNBT tag = stack.getOrCreateTag();
		if (tag.contains("BlockUsed") && NBTUtil.readBlockState(stack.getTag()
			.getCompound("BlockUsed")) == newState && Objects.equals(data, tag.get("BlockData"))) {
			return false;
		}

		tag.put("BlockUsed", NBTUtil.writeBlockState(newState));
		if (data == null)
			tag.remove("BlockData");
		else
			tag.put("BlockData", data);

		AllSoundEvents.CONFIRM.playOnServer(player.world, player.getBlockPos());
		return true;
	}

	public static int getRange(ItemStack stack) {
		if (stack.getItem() instanceof ZapperItem)
			return ((ZapperItem) stack.getItem()).getZappingRange(stack);
		return 0;
	}
}
