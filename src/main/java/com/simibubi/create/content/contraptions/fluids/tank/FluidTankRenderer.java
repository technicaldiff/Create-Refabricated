package com.simibubi.create.content.contraptions.fluids.tank;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.gui.widgets.InterpolatedChasingValue;
import com.simibubi.create.foundation.tileEntity.renderer.SafeTileEntityRenderer;
import com.simibubi.create.lib.lba.fluid.FluidStack;
import com.simibubi.create.lib.lba.fluid.SimpleFluidTank;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.MathHelper;

public class FluidTankRenderer extends SafeTileEntityRenderer<FluidTankTileEntity> {

	public FluidTankRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected void renderSafe(FluidTankTileEntity te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer,
		int light, int overlay) {
		if (!te.isController())
			return;
		if (!te.window)
			return;

		InterpolatedChasingValue fluidLevel = te.getFluidLevel();
		if (fluidLevel == null)
			return;

		float capHeight = 1 / 4f;
		float tankHullWidth = 1 / 16f + 1 / 128f;
		float minPuddleHeight = 1 / 16f;
		float totalHeight = te.height - 2 * capHeight - minPuddleHeight;

		float level = fluidLevel.get(partialTicks);
		if (level < 1 / (512f * totalHeight))
			return;
		float clampedLevel = MathHelper.clamp(level * totalHeight, 0, totalHeight);

		SimpleFluidTank tank = te.tankInventory;
		FluidStack fluidStack = (FluidStack) tank.getInvFluid();

		if (fluidStack.isEmpty())
			return;

		boolean top = fluidStack//.getFluid()
//			.getAttributes()
			.isLighterThanAir();

		float xMin = tankHullWidth;
		float xMax = xMin + te.width - 2 * tankHullWidth;
		float yMin = totalHeight + capHeight + minPuddleHeight - clampedLevel;
		float yMax = yMin + clampedLevel;

		if (top) {
			yMin += totalHeight - clampedLevel;
			yMax += totalHeight - clampedLevel;
		}

		float zMin = tankHullWidth;
		float zMax = zMin + te.width - 2 * tankHullWidth;

		ms.push();
		ms.translate(0, clampedLevel - totalHeight, 0);
		FluidRenderer.renderTiledFluidBB(fluidStack, xMin, yMin, zMin, xMax, yMax, zMax, buffer, ms, light, false);
		ms.pop();
	}

	@Override
	public boolean isGlobalRenderer(FluidTankTileEntity te) {
		return te.isController();
	}

}
