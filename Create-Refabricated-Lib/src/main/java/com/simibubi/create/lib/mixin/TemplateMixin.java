package com.simibubi.create.lib.mixin;

import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.lib.extensions.TemplateExtensions;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

@Mixin(Template.class)
public class TemplateMixin implements TemplateExtensions {
	@Shadow @Final private List<Template.EntityInfo> entities;

	@Override
	public List<Template.EntityInfo> getEntities() {
		return entities;
	}

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/world/gen/feature/template/Template;spawnEntities(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Mirror;Lnet/minecraft/util/Rotation;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/MutableBoundingBox;Z)V"),
			method = "Lnet/minecraft/world/gen/feature/template/Template;place(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/template/PlacementSettings;Ljava/util/Random;I)Z", cancellable = true)
	public void place(IServerWorld iServerWorld, BlockPos blockPos, BlockPos blockPos2, PlacementSettings placementSettings, Random random, int i, CallbackInfoReturnable<Boolean> cir) {
		addEntitiesToWorld(iServerWorld, blockPos, placementSettings);
		cir.setReturnValue(true);
	}
}
