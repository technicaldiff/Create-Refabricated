package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.simibubi.create.lib.extensions.DiggingParticleExtensions;
import com.simibubi.create.lib.utility.MixinHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.DiggingParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
@Mixin(DiggingParticle.class)
public abstract class DiggingParticleMixin extends SpriteTexturedParticle implements DiggingParticleExtensions {
	@Final
	@Shadow
	private final BlockState sourceState = null;

	private DiggingParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
		super(clientWorld, d, e, f);
	}

	@Override
	@Unique
	public Particle create$updateSprite(BlockPos pos) {
		if (pos != null)
			this.setSprite(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(sourceState));
		return MixinHelper.cast(this);
	}
}
