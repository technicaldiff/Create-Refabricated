package com.simibubi.create.content.contraptions.components.actors;

import com.simibubi.create.AllTileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import org.jetbrains.annotations.Nullable;

public class HarvesterBlock extends AttachedActorBlock implements ITileEntityProvider {

	public HarvesterBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}

//	@Override
//	public boolean hasTileEntity(BlockState state) {
//		return true;
//	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new HarvesterTileEntity(AllTileEntities.HARVESTER.get());
	}

}
