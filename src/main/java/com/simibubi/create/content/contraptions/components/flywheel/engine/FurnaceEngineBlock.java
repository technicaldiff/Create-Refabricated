package com.simibubi.create.content.contraptions.components.flywheel.engine;

import com.simibubi.create.AllBlockEntities;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.worldWrappers.WrappedWorld;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FurnaceEngineBlock extends EngineBlock implements IBE<FurnaceEngineBlockEntity>, BlockEntityProvider {

	public FurnaceEngineBlock(Settings properties) {
		super(properties);
	}

	@Override
	protected boolean isValidBaseBlock(BlockState baseBlock, BlockView world, BlockPos pos) {
		return baseBlock.getBlock() instanceof AbstractFurnaceBlock;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
		return AllShapes.FURNACE_ENGINE.get(state.get(FACING));
	}

	@Override
	public AllBlockPartials getFrameModel() {
		return AllBlockPartials.FURNACE_GENERATOR_FRAME;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return AllBlockEntities.FURNACE_ENGINE.instantiate();
	}

	@Override
	public void neighborUpdate(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		super.neighborUpdate(state, worldIn, pos, blockIn, fromPos, isMoving);
		if (worldIn instanceof WrappedWorld)
			return;
		if (worldIn.isClient)
			return;

		if (fromPos.equals(getBaseBlockPos(state, pos)))
			if (canPlaceAt(state, worldIn, pos))
				withBlockEntityDo(worldIn, pos, FurnaceEngineBlockEntity::updateFurnace);
	}

	/*public static void usingFurnaceEngineOnFurnacePreventsGUI(RightClickBlock event) {
		ItemStack item = event.getItemStack();
		if (!(item.getItem() instanceof BlockItem))
			return;
		BlockItem blockItem = (BlockItem) item.getItem();
		if (blockItem.getBlock() != AllBlocks.FURNACE_ENGINE.get())
			return;
		BlockState state = event.getWorld().getBlockState(event.getPos());
		if (event.getFace().getAxis().isVertical())
			return;
		if (state.getBlock() instanceof AbstractFurnaceBlock)
			event.setUseBlock(Result.DENY);
	}*/

	@Override
	public Class<FurnaceEngineBlockEntity> getBlockEntityClass() {
		return FurnaceEngineBlockEntity.class;
	}

}
