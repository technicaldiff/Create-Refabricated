package com.simibubi.create.foundation.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.events.custom.ModelsBakedCallback;
import com.simibubi.create.foundation.utility.extensions.BakedModelManagerExtensions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Environment(EnvType.CLIENT)
@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin implements BakedModelManagerExtensions {
	@Shadow
	private Map<Identifier, BakedModel> models;

	@Inject(at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = "ldc=cache"), method = "apply(Lnet/minecraft/client/render/model/ModelLoader;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V")
	public void onModelBake(ModelLoader modelLoader, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
		ModelsBakedCallback.EVENT.invoker().onModelsBaked((BakedModelManager) (Object) this, models, modelLoader);
	}

	@Override
	public BakedModel getModel(Identifier id) {
		return models.get(id);
	}
}
