// PORTED CREATE SOURCE

package com.simibubi.create.content.contraptions.components.structureMovement.gantry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.contraptions.base.Rotating;
import com.simibubi.create.content.contraptions.relays.advanced.GantryShaftBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Iterate;

public class GantryPinionBlock extends DirectionalAxisKineticBlock implements IBE<GantryPinionBlockEntity>, BlockEntityProvider {

	public GantryPinionBlock(Settings properties) {
		super(properties);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction direction = state.get(FACING);
		BlockState shaft = world.getBlockState(pos.offset(direction.getOpposite()));
		return AllBlocks.GANTRY_SHAFT.getStateManager().getStates().contains(shaft) && shaft.get(GantryShaftBlock.FACING)
			.getAxis() != direction.getAxis();
	}

	@Override
	public void prepare(BlockState stateIn, WorldAccess worldIn, BlockPos pos, int flags, int count) {
		super.prepare(stateIn, worldIn, pos, flags, count);
		withBlockEntityDo((World) worldIn, pos, GantryPinionBlockEntity::checkValidGantryShaft);
	}
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return AllBlockEntities.GANTRY_PINION.instantiate();
	}

	@Override
	protected Direction getFacingForPlacement(ItemPlacementContext context) {
		return context.getSide();
	}

	public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
		BlockHitResult hit) {
		if (!player.canModifyBlocks() || player.isSneaking())
			return ActionResult.PASS;
		if (player.getStackInHand(handIn)
			.isEmpty()) {
			withBlockEntityDo(worldIn, pos, te -> te.checkValidGantryShaft());
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		BlockState stateForPlacement = super.getPlacementState(context);
		Direction opposite = stateForPlacement.get(FACING)
			.getOpposite();
		return cycleAxisIfNecessary(stateForPlacement, opposite, context.getWorld()
			.getBlockState(context.getBlockPos()
				.offset(opposite)));
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block p_220069_4_, BlockPos p_220069_5_,
		boolean p_220069_6_) {
		if (!canPlaceAt(state, world, pos))
			world.breakBlock(pos, true);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState otherState, WorldAccess world,
		BlockPos pos, BlockPos p_196271_6_) {
		if (state.get(FACING) != direction.getOpposite())
			return state;
		return cycleAxisIfNecessary(state, direction, otherState);
	}

	protected BlockState cycleAxisIfNecessary(BlockState state, Direction direction, BlockState otherState) {
		if (!AllBlocks.GANTRY_SHAFT.getStateManager().getStates().contains(otherState))
			return state;
		if (otherState.get(GantryShaftBlock.FACING)
			.getAxis() == direction.getAxis())
			return state;
		if (isValidGantryShaftAxis(state, otherState))
			return state;
		return state.cycle(AXIS_ALONG_FIRST_COORDINATE);
	}

	public static boolean isValidGantryShaftAxis(BlockState pinionState, BlockState gantryState) {
		return getValidGantryShaftAxis(pinionState) == gantryState.get(GantryShaftBlock.FACING)
			.getAxis();
	}

	public static Axis getValidGantryShaftAxis(BlockState state) {
		if (!(state.getBlock() instanceof GantryPinionBlock))
			return Axis.Y;
		Rotating block = (Rotating) state.getBlock();
		Axis rotationAxis = block.getRotationAxis(state);
		Axis facingAxis = state.get(FACING)
			.getAxis();
		for (Axis axis : Iterate.axes)
			if (axis != rotationAxis && axis != facingAxis)
				return axis;
		return Axis.Y;
	}

	public static Axis getValidGantryPinionAxis(BlockState state, Axis shaftAxis) {
		Axis facingAxis = state.get(FACING)
			.getAxis();
		for (Axis axis : Iterate.axes)
			if (axis != shaftAxis && axis != facingAxis)
				return axis;
		return Axis.Y;
	}

	@Override
	public Class<GantryPinionBlockEntity> getBlockEntityClass() {
		return GantryPinionBlockEntity.class;
	}

}
