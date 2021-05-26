package com.simibubi.create.content.contraptions.goggles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.AllBlockPartials;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;

public class GogglesModel extends ForwardingBakedModel {

	public GogglesModel(IBakedModel template) {
		wrapped = template;
	}

	@Override
	public IBakedModel handlePerspective(TransformType cameraTransformType, MatrixStack mat) {
		if (cameraTransformType == TransformType.HEAD)
			return AllBlockPartials.GOGGLES.get()
				.handlePerspective(cameraTransformType, mat);
		return super.handlePerspective(cameraTransformType, mat);
	}
}
