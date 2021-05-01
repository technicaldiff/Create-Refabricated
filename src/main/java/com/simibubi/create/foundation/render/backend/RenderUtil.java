package com.simibubi.create.foundation.render.backend;

import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.MatrixStacker;
import com.simibubi.create.lib.helper.Matrix3fHelper;
import com.simibubi.create.lib.helper.Matrix4fHelper;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;

public class RenderUtil {
	public static int nextPowerOf2(int a) {
		int h = Integer.highestOneBit(a);
		return (h == a) ? h : (h << 1);
	}

	public static boolean isPowerOf2(int n) {
		int b = n & (n - 1);
		return b == 0 && n != 0;
	}

	public static float[] writeMatrixStack(MatrixStack stack) {
		return writeMatrixStack(stack.peek().getModel(), stack.peek().getNormal());
	}

	// GPUs want matrices in column major order.
	public static float[] writeMatrixStack(Matrix4f model, Matrix3f normal) {
		return ArrayUtils.addAll(Matrix4fHelper.writeMatrix(model), Matrix3fHelper.writeMatrix(normal));
	}

	public static float[] writeMatrix(Matrix4f model) {
		return Matrix4fHelper.writeMatrix(model);
	}

	public static Supplier<MatrixStack> rotateToFace(Direction facing) {
		return () -> {
			MatrixStack stack = new MatrixStack();
			MatrixStacker.of(stack)
					.centre()
					.rotateY(AngleHelper.horizontalAngle(facing))
					.rotateX(AngleHelper.verticalAngle(facing))
					.unCentre();
			return stack;
		};
	}
}
