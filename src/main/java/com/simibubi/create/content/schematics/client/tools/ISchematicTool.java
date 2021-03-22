package com.simibubi.create.content.schematics.client.tools;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import com.simibubi.create.foundation.renderState.SuperRenderTypeBuffer;

public interface ISchematicTool {

	public void init();
	public void updateSelection();
	
	public boolean handleRightClick();
	public boolean handleMouseWheel(double delta);
	
	public void renderTool(MatrixStack ms, SuperRenderTypeBuffer buffer);
	public void renderOverlay(MatrixStack ms, VertexConsumerProvider buffer);
	public void renderOnSchematic(MatrixStack ms, SuperRenderTypeBuffer buffer);
	
}
