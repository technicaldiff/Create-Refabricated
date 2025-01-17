/*
package com.simibubi.create.content.contraptions.components.structureMovement.piston;

import com.simibubi.create.content.contraptions.components.structureMovement.piston.MechanicalPistonBlock.PistonState;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.state.properties.PistonType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;

public class MechanicalPistonGenerator extends SpecialBlockStateGen {

	private final PistonType type;

	public MechanicalPistonGenerator(PistonType type) {
		this.type = type;
	}

	@Override
	protected int getXRotation(BlockState state) {
		Direction facing = state.get(MechanicalPistonBlock.FACING);
		return facing.getAxis()
			.isVertical() ? facing == Direction.DOWN ? 180 : 0 : 90;
	}

	@Override
	protected int getYRotation(BlockState state) {
		Direction facing = state.get(MechanicalPistonBlock.FACING);
		return facing.getAxis()
			.isVertical() ? 0 : horizontalAngle(facing) + 180;
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
		BlockState state) {
		Direction facing = state.get(PistonBlock.FACING);
		boolean axisAlongFirst = state.get(MechanicalPistonBlock.AXIS_ALONG_FIRST_COORDINATE);
		PistonState pistonState = state.get(MechanicalPistonBlock.STATE);

		String path = "block/mechanical_piston";
		String folder = pistonState == PistonState.RETRACTED ? type.getString() : pistonState.getString();
		String partial = facing.getAxis() == Axis.X ^ axisAlongFirst ? "block_rotated" : "block";

		return prov.models()
			.getExistingFile(prov.modLoc(path + "/" + folder + "/" + partial));
	}

}
*/
