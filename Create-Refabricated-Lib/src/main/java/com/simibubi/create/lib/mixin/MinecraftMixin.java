package com.simibubi.create.lib.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.event.BeforeFirstReloadCallback;
import com.simibubi.create.lib.event.ClientWorldEvents;
import com.simibubi.create.lib.event.InstanceRegistrationCallback;
import com.simibubi.create.lib.event.LeftClickAirCallback;
import com.simibubi.create.lib.event.ParticleManagerRegistrationCallback;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	public ClientWorld world;
	@Shadow
	public ClientPlayerEntity player;

	@Inject(at = @At(value = "NEW", target = "Lnet/minecraft/client/particle/ParticleManager", shift = Shift.AFTER), method = "<init>")
	public void create$registerParticleManagers(GameConfiguration gameConfiguration, CallbackInfo ci) {
		ParticleManagerRegistrationCallback.EVENT.invoker().onParticleManagerRegistration();
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setLoadingGui(Lnet/minecraft/client/gui/LoadingGui;)V"), method = "<init>(Lnet/minecraft/client/GameConfiguration;)V")
	public void create$beforeFirstReload(GameConfiguration args, CallbackInfo ci) {
		BeforeFirstReloadCallback.EVENT.invoker().beforeFirstReload((Minecraft) (Object) this);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/SimpleReloadableResourceManager;<init>(Lnet/minecraft/resources/ResourcePackType;)V"),
			method = "<init>(Lnet/minecraft/client/GameConfiguration;)V") // should inject to right after the initialization of resourceManager
	public void create$instanceRegistration(GameConfiguration args, CallbackInfo ci) {
		InstanceRegistrationCallback.EVENT.invoker().registerInstance();
	}

	@Inject(at = @At("HEAD"), method = "loadWorld(Lnet/minecraft/client/world/ClientWorld;)V")
	public void create$onHeadJoinWorld(ClientWorld world, CallbackInfo ci) {
		if (this.world != null) {
			ClientWorldEvents.UNLOAD.invoker().onWorldUnload((Minecraft) (Object) this, this.world);
		}
	}

	@Inject(at = @At(value = "JUMP", opcode = Opcodes.IFNULL, ordinal = 1, shift = Shift.AFTER), method = "func_213231_b(Lnet/minecraft/client/gui/screen/Screen;)V")
	public void create$onDisconnect(Screen screen, CallbackInfo ci) {
		ClientWorldEvents.UNLOAD.invoker().onWorldUnload((Minecraft) (Object) this, this.world);
	}

	@Inject(method = "clickMouse()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;resetLastAttackedTicks()V"))
	private void create$onClickMouse(CallbackInfo ci) {
		LeftClickAirCallback.EVENT.invoker().onLeftClickAir(player);
	}
}
