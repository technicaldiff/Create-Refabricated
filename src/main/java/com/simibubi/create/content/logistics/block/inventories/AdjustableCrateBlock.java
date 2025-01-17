package com.simibubi.create.content.logistics.block.inventories;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTileEntities;
import com.simibubi.create.foundation.item.ItemHelper;

import com.simibubi.create.lib.utility.NetworkUtil;

import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class AdjustableCrateBlock extends CrateBlock implements ITileEntityProvider {

	public AdjustableCrateBlock(Properties p_i48415_1_) {
		super(p_i48415_1_);
	}

//	@Override
//	public boolean hasTileEntity(BlockState state) {
//		return true;
//	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return AllTileEntities.ADJUSTABLE_CRATE.create();
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (oldState.getBlock() != state.getBlock() && state.getBlock().hasBlockEntity() && state.get(DOUBLE)
				&& state.get(FACING).getAxisDirection() == AxisDirection.POSITIVE) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (!(tileEntity instanceof AdjustableCrateTileEntity))
				return;

			AdjustableCrateTileEntity te = (AdjustableCrateTileEntity) tileEntity;
			AdjustableCrateTileEntity other = te.getOtherCrate();
			if (other == null)
				return;

			for (int slot = 0; slot < other.inventory.getSlots(); slot++) {
				te.inventory.setStackInSlot(slot, other.inventory.getStackInSlot(slot));
				other.inventory.setStackInSlot(slot, ItemStack.EMPTY);
			}
			te.allowedAmount = other.allowedAmount;
			other.invHandler.invalidate();
		}
	}

	@Override
	public ActionResultType onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {

		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof AdjustableCrateTileEntity) {
				AdjustableCrateTileEntity fte = (AdjustableCrateTileEntity) te;
				fte = fte.getMainCrate();
				NetworkUtil.openGUI((ServerPlayerEntity) player, fte, fte::sendToContainer);
			}
			return ActionResultType.SUCCESS;
		}
	}

	public static void splitCrate(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (!AllBlocks.ADJUSTABLE_CRATE.has(state))
			return;
		if (!state.get(DOUBLE))
			return;
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof AdjustableCrateTileEntity))
			return;
		AdjustableCrateTileEntity crateTe = (AdjustableCrateTileEntity) te;
		crateTe.onSplit();
		world.setBlockState(pos, state.with(DOUBLE, false));
		world.setBlockState(crateTe.getOtherCrate().getPos(), state.with(DOUBLE, false));
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!(worldIn.getTileEntity(pos) instanceof AdjustableCrateTileEntity))
			return;

		if (state.getBlock().hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			AdjustableCrateTileEntity te = (AdjustableCrateTileEntity) worldIn.getTileEntity(pos);
			if (!isMoving)
				te.onDestroyed();
			worldIn.removeTileEntity(pos);
		}

	}

	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof AdjustableCrateTileEntity) {
			AdjustableCrateTileEntity flexcrateTileEntity = ((AdjustableCrateTileEntity) te).getMainCrate();
			return ItemHelper.calcRedstoneFromInventory(flexcrateTileEntity.inventory);
		}
		return 0;
	}

}
