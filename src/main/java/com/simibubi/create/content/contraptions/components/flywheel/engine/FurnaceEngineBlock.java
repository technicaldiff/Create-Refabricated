package com.simibubi.create.content.contraptions.components.flywheel.engine;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.AllTileEntities;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.utility.worldWrappers.WrappedWorld;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FurnaceEngineBlock extends EngineBlock implements ITE<FurnaceEngineTileEntity>, ITileEntityProvider {

	public FurnaceEngineBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected boolean isValidBaseBlock(BlockState baseBlock, IBlockReader world, BlockPos pos) {
		return baseBlock.getBlock() instanceof AbstractFurnaceBlock;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return AllShapes.FURNACE_ENGINE.get(state.get(HORIZONTAL_FACING));
	}

	@Override
	public PartialModel getFrameModel() {
		return AllBlockPartials.FURNACE_GENERATOR_FRAME;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return AllTileEntities.FURNACE_ENGINE.create();
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
		if (worldIn instanceof WrappedWorld)
			return;
		if (worldIn.isRemote)
			return;

		if (fromPos.equals(getBaseBlockPos(state, pos)))
			if (isValidPosition(state, worldIn, pos))
				withTileEntityDo(worldIn, pos, FurnaceEngineTileEntity::updateFurnace);
	}

	public static ActionResultType usingFurnaceEngineOnFurnacePreventsGUI(PlayerEntity player, World world, Hand hand, BlockRayTraceResult hitResult) {
		ItemStack heldStack = player.getHeldItem(hand);
		if (!(heldStack.getItem() instanceof BlockItem))
			return ActionResultType.PASS;
		BlockItem blockItem = (BlockItem) heldStack.getItem();
		if (blockItem.getBlock() != AllBlocks.FURNACE_ENGINE.get())
			return ActionResultType.PASS;
		BlockState state = world.getBlockState(hitResult.getPos());
		if (hitResult.getFace().getAxis().isVertical())
			return ActionResultType.PASS;
		if (state.getBlock() instanceof AbstractFurnaceBlock)
			return ActionResultType.SUCCESS;
		return ActionResultType.PASS;
	}

	@Override
	public Class<FurnaceEngineTileEntity> getTileEntityClass() {
		return FurnaceEngineTileEntity.class;
	}

}
