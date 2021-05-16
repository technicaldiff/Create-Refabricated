package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.simibubi.create.lib.extensions.BlockParticleDataExtensions;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.particles.BlockParticleData;
import net.minecraft.util.math.BlockPos;

@Mixin(BlockParticleData.class)
public abstract class BlockParticleDataMixin implements BlockParticleDataExtensions {
	private BlockPos pos;

	@Override @Unique
	public BlockParticleData setPos(BlockPos pos) {
		this.pos = pos;
		return MixinHelper.cast(this);
	}

	@Override @Unique
	public BlockPos getPos() {
		return pos;
	}
}
