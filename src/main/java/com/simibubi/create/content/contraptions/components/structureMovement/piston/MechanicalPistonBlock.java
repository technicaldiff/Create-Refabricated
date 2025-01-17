package com.simibubi.create.content.contraptions.components.structureMovement.piston;

import java.util.Random;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.AllTileEntities;
import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MechanicalPistonBlock extends DirectionalAxisKineticBlock implements ITE<MechanicalPistonTileEntity> {

	public static final EnumProperty<PistonState> STATE = EnumProperty.create("state", PistonState.class);
	protected boolean isSticky;

	public static MechanicalPistonBlock normal(Properties properties) {
		return new MechanicalPistonBlock(properties, false);
	}

	public static MechanicalPistonBlock sticky(Properties properties) {
		return new MechanicalPistonBlock(properties, true);
	}

	protected MechanicalPistonBlock(Properties properties, boolean sticky) {
		super(properties);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH)
			.with(STATE, PistonState.RETRACTED));
		isSticky = sticky;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(STATE);
		super.fillStateContainer(builder);
	}

	@Override
	public ActionResultType onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
		BlockRayTraceResult hit) {
		if (!player.isAllowEdit())
			return ActionResultType.PASS;
		if (player.isSneaking())
			return ActionResultType.PASS;
		if (!(player.getHeldItem(handIn)
			.getItem()
			/*.isIn(TagUtil.SLIMEBALLS)*/== Items.SLIME_BALL)) {
			if (player.getHeldItem(handIn)
				.isEmpty()) {
				withTileEntityDo(worldIn, pos, te -> te.assembleNextTick = true);
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.PASS;
		}
		if (state.get(STATE) != PistonState.RETRACTED)
			return ActionResultType.PASS;
		Direction direction = state.get(FACING);
		if (hit.getFace() != direction)
			return ActionResultType.PASS;
		if (((MechanicalPistonBlock) state.getBlock()).isSticky)
			return ActionResultType.PASS;
		if (worldIn.isRemote) {
			Vector3d vec = hit.getHitVec();
			worldIn.addParticle(ParticleTypes.ITEM_SLIME, vec.x, vec.y, vec.z, 0, 0, 0);
			return ActionResultType.SUCCESS;
		}
		AllSoundEvents.SLIME_ADDED.playOnServer(worldIn, pos, .5f, 1);
		if (!player.isCreative())
			player.getHeldItem(handIn)
				.shrink(1);
		worldIn.setBlockState(pos, AllBlocks.STICKY_MECHANICAL_PISTON.getDefaultState()
			.with(FACING, direction)
			.with(AXIS_ALONG_FIRST_COORDINATE, state.get(AXIS_ALONG_FIRST_COORDINATE)));
		return ActionResultType.SUCCESS;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block p_220069_4_, BlockPos fromPos,
		boolean p_220069_6_) {
		Direction direction = state.get(FACING);
		if (!fromPos.equals(pos.offset(direction.getOpposite())))
			return;
		if (!world.isRemote && !world.getPendingBlockTicks()
			.isTickPending(pos, this))
			world.getPendingBlockTicks()
				.scheduleTick(pos, this, 0);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random r) {
		Direction direction = state.get(FACING);
		BlockState pole = worldIn.getBlockState(pos.offset(direction.getOpposite()));
		if (!AllBlocks.PISTON_EXTENSION_POLE.has(pole))
			return;
		if (pole.get(PistonExtensionPoleBlock.FACING)
			.getAxis() != direction.getAxis())
			return;
		withTileEntityDo(worldIn, pos, te -> {
			if (te.lastException == null)
				return;
			te.lastException = null;
			te.sendData();
		});
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return AllTileEntities.MECHANICAL_PISTON.create();
	}

	@Override
	public ActionResultType onWrenched(BlockState state, ItemUseContext context) {
		if (state.get(STATE) != PistonState.RETRACTED)
			return ActionResultType.PASS;
		return super.onWrenched(state, context);
	}

	public enum PistonState implements IStringSerializable {
		RETRACTED, MOVING, EXTENDED;

		@Override
		public String getString() {
			return Lang.asId(name());
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		Direction direction = state.get(FACING);
		BlockPos pistonHead = null;
		BlockPos pistonBase = pos;
		boolean dropBlocks = player == null || !player.isCreative();

		Integer maxPoles = maxAllowedPistonPoles();
		for (int offset = 1; offset < maxPoles; offset++) {
			BlockPos currentPos = pos.offset(direction, offset);
			BlockState block = worldIn.getBlockState(currentPos);

			if (isExtensionPole(block) && direction.getAxis() == block.get(BlockStateProperties.FACING)
				.getAxis())
				continue;

			if (isPistonHead(block) && block.get(BlockStateProperties.FACING) == direction) {
				pistonHead = currentPos;
			}

			break;
		}

		if (pistonHead != null && pistonBase != null) {
			BlockPos.getAllInBox(pistonBase, pistonHead)
				.filter(p -> !p.equals(pos))
				.forEach(p -> worldIn.destroyBlock(p, dropBlocks));
		}

		for (int offset = 1; offset < maxPoles; offset++) {
			BlockPos currentPos = pos.offset(direction.getOpposite(), offset);
			BlockState block = worldIn.getBlockState(currentPos);

			if (isExtensionPole(block) && direction.getAxis() == block.get(BlockStateProperties.FACING)
				.getAxis()) {
				worldIn.destroyBlock(currentPos, dropBlocks);
				continue;
			}

			break;
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	public static int maxAllowedPistonPoles() {
		return AllConfigs.SERVER.kinetics.maxPistonPoles.get();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

		if (state.get(STATE) == PistonState.EXTENDED)
			return AllShapes.MECHANICAL_PISTON_EXTENDED.get(state.get(FACING));

		if (state.get(STATE) == PistonState.MOVING)
			return AllShapes.MECHANICAL_PISTON.get(state.get(FACING));

		return VoxelShapes.fullCube();
	}

	@Override
	public Class<MechanicalPistonTileEntity> getTileEntityClass() {
		return MechanicalPistonTileEntity.class;
	}

	public static boolean isPiston(BlockState state) {
		return AllBlocks.MECHANICAL_PISTON.has(state) || isStickyPiston(state);
	}

	public static boolean isStickyPiston(BlockState state) {
		return AllBlocks.STICKY_MECHANICAL_PISTON.has(state);
	}

	public static boolean isExtensionPole(BlockState state) {
		return AllBlocks.PISTON_EXTENSION_POLE.has(state);
	}

	public static boolean isPistonHead(BlockState state) {
		return AllBlocks.MECHANICAL_PISTON_HEAD.has(state);
	}
}
