package com.simibubi.create.content.contraptions.relays.encased;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class AdjustablePulleyBlock extends EncasedBeltBlock implements IBE<AdjustablePulleyBlockEntity> {

	public static BooleanProperty POWERED = Properties.POWERED;

	public AdjustablePulleyBlock(Settings properties) {
		super(properties);
		setDefaultState(getDefaultState().with(POWERED, false));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		super.appendProperties(builder.add(POWERED));
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return AllBlockEntities.ADJUSTABLE_PULLEY.instantiate();
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
		if (oldState.getBlock() == state.getBlock())
			return;
		withBlockEntityDo(worldIn, pos, AdjustablePulleyBlockEntity::neighborChanged);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return super.getPlacementState(context).with(POWERED, context.getWorld()
			.isReceivingRedstonePower(context.getBlockPos()));
	}

	@Override
	protected boolean areStatesKineticallyEquivalent(BlockState oldState, BlockState newState) {
		return super.areStatesKineticallyEquivalent(oldState, newState)
			&& oldState.get(POWERED) == newState.get(POWERED);
	}

	@Override
	public void neighborUpdate(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
		boolean isMoving) {
		if (worldIn.isClient)
			return;

		withBlockEntityDo(worldIn, pos, AdjustablePulleyBlockEntity::neighborChanged);

		boolean previouslyPowered = state.get(POWERED);
		if (previouslyPowered != worldIn.isReceivingRedstonePower(pos))
			worldIn.setBlockState(pos, state.cycle(POWERED), 18);
	}

	@Override
	public Class<AdjustablePulleyBlockEntity> getBlockEntityClass() {
		return AdjustablePulleyBlockEntity.class;
	}

}
