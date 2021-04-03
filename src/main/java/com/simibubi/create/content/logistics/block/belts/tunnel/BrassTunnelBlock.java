// PORTED CREATE SOURCE

package com.simibubi.create.content.logistics.block.belts.tunnel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import com.simibubi.create.AllBlockEntities;

public class BrassTunnelBlock extends BeltTunnelBlock implements BlockEntityProvider {

	public BrassTunnelBlock(Settings properties) {
		super(properties);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return AllBlockEntities.BRASS_TUNNEL.instantiate();
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState facingState, WorldAccess worldIn,
		BlockPos currentPos, BlockPos facingPos) {
		return super.getStateForNeighborUpdate(state, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public void onStateReplaced(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_,
		boolean p_196243_5_) {
		if (p_196243_1_.getBlock().hasBlockEntity()
			&& (p_196243_1_.getBlock() != p_196243_4_.getBlock() || !p_196243_4_.getBlock().hasBlockEntity())) {
			//BlockEntityBehaviour.destroy(p_196243_2_, p_196243_3_, FilteringBehaviour.TYPE); todo: filters
			withBlockEntityDo(p_196243_2_, p_196243_3_, te -> {
				if (te instanceof BrassTunnelTileEntity)
					Block.dropStack(p_196243_2_, p_196243_3_, ((BrassTunnelTileEntity) te).stackToDistribute);
			});
			p_196243_2_.removeBlockEntity(p_196243_3_);
		}
	}

}
