package com.simibubi.create.compat.jei.category.animations;

//public class AnimatedCrushingWheels extends AnimatedKinetics {
//
//	@Override
//	public void draw(MatrixStack matrixStack, int xOffset, int yOffset) {
//		matrixStack.push();
//		matrixStack.translate(xOffset, yOffset, 100);
//		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-22.5f));
//		int scale = 22;
//
//		BlockState wheel = AllBlocks.CRUSHING_WHEEL.get()
//				.getDefaultState()
//				.with(BlockStateProperties.AXIS, Axis.X);
//
//		GuiGameElement.of(wheel)
//				.rotateBlock(0, 90, -getCurrentAngle())
//				.scale(scale)
//				.render(matrixStack);
//
//		GuiGameElement.of(wheel)
//				.rotateBlock(0, 90, getCurrentAngle())
//				.atLocal(2, 0, 0)
//				.scale(scale)
//				.render(matrixStack);
//
//		matrixStack.pop();
//	}
//
//}
