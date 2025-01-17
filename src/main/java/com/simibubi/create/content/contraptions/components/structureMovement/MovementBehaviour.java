package com.simibubi.create.content.contraptions.components.structureMovement;

import javax.annotation.Nullable;

import com.jozufozu.flywheel.backend.instancing.MaterialManager;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ActorInstance;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionKineticRenderer;
import com.simibubi.create.lib.lba.item.ItemHandlerHelper;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionMatrices;
import com.simibubi.create.foundation.utility.worldWrappers.PlacementSimulationWorld;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public abstract class MovementBehaviour {

	public boolean isActive(MovementContext context) {
		return true;
	}

	public void tick(MovementContext context) {}

	public void startMoving(MovementContext context) {}

	public void visitNewPosition(MovementContext context, BlockPos pos) {}

	public Vector3d getActiveAreaOffset(MovementContext context) {
		return Vector3d.ZERO;
	}

	public void dropItem(MovementContext context, ItemStack stack) {
		ItemStack remainder = ItemHandlerHelper.insertItem(context.contraption.inventory, stack, false);
		if (remainder.isEmpty())
			return;

		Vector3d vec = context.position;
		ItemEntity itemEntity = new ItemEntity(context.world, vec.x, vec.y, vec.z, remainder);
		itemEntity.setMotion(context.motion.add(0, 0.5f, 0)
			.scale(context.world.rand.nextFloat() * .3f));
		context.world.addEntity(itemEntity);
	}

	public void stopMoving(MovementContext context) {

	}

	public void writeExtraData(MovementContext context) {

	}

	public boolean renderAsNormalTileEntity() {
		return false;
	}

	public boolean hasSpecialInstancedRendering() {
		return false;
	}

	@Environment(EnvType.CLIENT)
	public void renderInContraption(MovementContext context, PlacementSimulationWorld renderWorld,
		ContraptionMatrices matrices, IRenderTypeBuffer buffer) {}

	@Environment(EnvType.CLIENT)
	@Nullable
	public ActorInstance createInstance(MaterialManager<?> materialManager, PlacementSimulationWorld simulationWorld, MovementContext context) {
		return null;
	}

	public void onSpeedChanged(MovementContext context, Vector3d oldMotion, Vector3d motion) {
	}
}
