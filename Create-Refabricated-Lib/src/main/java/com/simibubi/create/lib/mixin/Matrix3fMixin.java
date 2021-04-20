package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.simibubi.create.lib.extensions.Matrix3fExtensions;

import net.minecraft.util.math.vector.Matrix3f;

@Mixin(Matrix3f.class)
public abstract class Matrix3fMixin implements Matrix3fExtensions {
	@Shadow
	protected float a00;
	@Shadow
	protected float a01;
	@Shadow
	protected float a02;
	@Shadow
	protected float a10;
	@Shadow
	protected float a11;
	@Shadow
	protected float a12;
	@Shadow
	protected float a20;
	@Shadow
	protected float a21;
	@Shadow
	protected float a22;

	@Override
	public float[] create$writeMatrix() {
		return new float[]{
				a00,
				a10,
				a20,
				a01,
				a11,
				a21,
				a02,
				a12,
				a22,
		};
	}
}
