package com.simibubi.create.content.logistics.block.mechanicalArm;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.foundation.block.IBE;
import org.apache.commons.lang3.mutable.MutableBoolean;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmBlockEntity.Phase;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class ArmBlock extends KineticBlock implements IBE<ArmBlockEntity> {

	public static final BooleanProperty CEILING = BooleanProperty.of("ceiling");

	public ArmBlock(Settings properties) {
		super(properties);
		setDefaultState(getDefaultState().with(CEILING, false));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> p_206840_1_) {
		super.appendProperties(p_206840_1_.add(CEILING));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return getDefaultState().with(CEILING, ctx.getSide() == Direction.DOWN);
	}

	@Override
	public boolean hasIntegratedCogwheel(WorldView world, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView p_220053_2_, BlockPos p_220053_3_,
		ShapeContext p_220053_4_) {
		return state.get(CEILING) ? AllShapes.MECHANICAL_ARM_CEILING : AllShapes.MECHANICAL_ARM;
	}
	
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onBlockAdded(state, world, pos, oldState, isMoving);
		withBlockEntityDo(world, pos, ArmBlockEntity::redstoneUpdate);
	}
	
	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block p_220069_4_,
		BlockPos p_220069_5_, boolean p_220069_6_) {
		withBlockEntityDo(world, pos, ArmBlockEntity::redstoneUpdate);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return Axis.Y;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return AllBlockEntities.MECHANICAL_ARM.instantiate();
	}

	@Override
	public Class<ArmBlockEntity> getBlockEntityClass() {
		return ArmBlockEntity.class;
	}

	@Override
	public void onStateReplaced(BlockState p_196243_1_, World world, BlockPos pos, BlockState p_196243_4_,
		boolean p_196243_5_) {
		/*if (p_196243_1_.hasTileEntity()
			&& (p_196243_1_.getBlock() != p_196243_4_.getBlock() || !p_196243_4_.hasTileEntity())) {
			withBlockEntityDo(world, pos, te -> {
				if (!te.heldItem.isEmpty())
					ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), te.heldItem);
			});
			world.removeBlockEntity(pos);
		}*/
	}

	@Override
	public ActionResult onUse(BlockState p_225533_1_, World world, BlockPos pos, PlayerEntity player,
		Hand p_225533_5_, BlockHitResult p_225533_6_) {
		MutableBoolean success = new MutableBoolean(false);
		withBlockEntityDo(world, pos, te -> {
			if (te.heldItem.isEmpty())
				return;
			success.setTrue();
			if (world.isClient)
				return;
			player.inventory.offerOrDrop(world, te.heldItem);
			te.heldItem = ItemStack.EMPTY;
			te.phase = Phase.SEARCH_INPUTS;
			te.markDirty();
			te.sendData();
		});
		
		return success.booleanValue() ? ActionResult.SUCCESS : ActionResult.PASS;
	}

}
