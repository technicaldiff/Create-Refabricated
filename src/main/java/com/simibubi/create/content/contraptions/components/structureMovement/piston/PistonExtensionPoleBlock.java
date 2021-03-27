package com.simibubi.create.content.contraptions.components.structureMovement.piston;

import static com.simibubi.create.content.contraptions.components.structureMovement.piston.MechanicalPistonBlock.isExtensionPole;
import static com.simibubi.create.content.contraptions.components.structureMovement.piston.MechanicalPistonBlock.isPiston;
import static com.simibubi.create.content.contraptions.components.structureMovement.piston.MechanicalPistonBlock.isPistonHead;

import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction.AxisDirection;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.contraptions.components.structureMovement.piston.MechanicalPistonBlock.PistonState;
import com.simibubi.create.content.contraptions.wrench.Wrenchable;
import com.simibubi.create.foundation.block.ProperDirectionalBlock;
import com.simibubi.create.foundation.utility.placement.util.PoleHelper;

public class PistonExtensionPoleBlock extends ProperDirectionalBlock implements Wrenchable, Waterloggable {

    //private static final int placementHelperId = PlacementHelpers.register(PolePlacementHelper.get());

    public PistonExtensionPoleBlock(Settings properties) {
        super(properties);
        setDefaultState(getDefaultState().with(FACING, Direction.UP).with(Properties.WATERLOGGED, false));
    }

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.NORMAL;
	}

	@Override
	public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		Axis axis = state.get(FACING)
			.getAxis();
		Direction direction = Direction.get(AxisDirection.POSITIVE, axis);
		BlockPos pistonHead = null;
		BlockPos pistonBase = null;

		for (int modifier : new int[] { 1, -1 }) {
			for (int offset = modifier; modifier * offset < MechanicalPistonBlock.maxAllowedPistonPoles(); offset +=
				modifier) {
				BlockPos currentPos = pos.offset(direction, offset);
				BlockState block = worldIn.getBlockState(currentPos);

				if (isExtensionPole(block) && axis == block.get(FACING)
					.getAxis())
					continue;

				if (isPiston(block) && block.get(Properties.FACING)
					.getAxis() == axis)
					pistonBase = currentPos;

				if (isPistonHead(block) && block.get(Properties.FACING)
					.getAxis() == axis)
					pistonHead = currentPos;

				break;
			}
		}

		if (pistonHead != null && pistonBase != null && worldIn.getBlockState(pistonHead)
			.get(Properties.FACING) == worldIn.getBlockState(pistonBase)
				.get(Properties.FACING)) {

			final BlockPos basePos = pistonBase;
			BlockPos.stream(pistonBase, pistonHead)
				.filter(p -> !p.equals(pos) && !p.equals(basePos))
				.forEach(p -> worldIn.breakBlock(p, !player.isCreative()));
			worldIn.setBlockState(basePos, worldIn.getBlockState(basePos)
				.with(MechanicalPistonBlock.STATE, PistonState.RETRACTED));
		}

		super.onBreak(worldIn, pos, state, player);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
		return AllShapes.FOUR_VOXEL_POLE.get(state.get(FACING)
			.getAxis());
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		FluidState FluidState = context.getWorld()
			.getFluidState(context.getBlockPos());
		return getDefaultState().with(FACING, context.getSide()
			.getOpposite())
			.with(Properties.WATERLOGGED, Boolean.valueOf(FluidState.getFluid() == Fluids.WATER));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
		BlockHitResult ray) {
		ItemStack heldItem = player.getStackInHand(hand);

        /*PolePlacementHelper placementHelper = (PolePlacementHelper) PlacementHelpers.get(placementHelperId);
        if (placementHelper.matchesItem(heldItem) && !player.isSneaking())
	   		return placementHelper.getOffset(world, state, pos, ray).placeInWorld(world, (BlockItem) heldItem.getItem(), player, hand, ray);*/

		return ActionResult.PASS;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false)
			: Fluids.EMPTY.getDefaultState();
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(Properties.WATERLOGGED);
		super.appendProperties(builder);
	}

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbourState, WorldAccess world, BlockPos pos, BlockPos neighbourPos) {
        if (state.get(Properties.WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

//    @MethodsReturnNonnullByDefault
    public static class PolePlacementHelper extends PoleHelper<Direction> {

        private static final PolePlacementHelper instance = new PolePlacementHelper();

        public static PolePlacementHelper get() {
            return instance;
        }

        private PolePlacementHelper() {
            super(
                    AllBlocks.PISTON_EXTENSION_POLE.stateManager.getStates()::contains,
                    state -> state.get(FACING).getAxis(),
                    FACING
            );
        }

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return s -> s.isItemEqualIgnoreDamage(AllBlocks.PISTON_EXTENSION_POLE.asItem().getDefaultStack());
        }
    }
}
