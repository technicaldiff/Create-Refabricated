package com.simibubi.create.lib.mixin;

import com.simibubi.create.lib.block.CustomPickBlockBehavior;
import com.simibubi.create.lib.utility.MixinHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;

import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.event.BeforeFirstReloadCallback;
import com.simibubi.create.lib.event.ClientWorldEvents;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	private ClientWorld world;

	@Shadow
	@Nullable
	public ClientPlayerEntity player;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setLoadingGui(Lnet/minecraft/client/gui/LoadingGui;)V"), method = "<init>(Lnet/minecraft/client/GameConfiguration;)V")
	public void create$beforeFirstReload(GameConfiguration args, CallbackInfo ci) {
		BeforeFirstReloadCallback.EVENT.invoker().beforeFirstReload((Minecraft) (Object) this);
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

	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;middleClickMouse()V"), method = "Lnet/minecraft/client/Minecraft;middleClickMouse()V", cancellable = true)
	private void create$middleClickMouse(CallbackInfo ci) {
		if (MixinHelper.<Minecraft>cast(this).objectMouseOver.getType() == RayTraceResult.Type.BLOCK) {
			BlockState blockstate = world.getBlockState(((BlockRayTraceResult) MixinHelper.<Minecraft>cast(this).objectMouseOver).getPos());
			if (blockstate.getBlock() instanceof CustomPickBlockBehavior) {
				((CustomPickBlockBehavior) blockstate.getBlock()).getPickBlock(blockstate, MixinHelper.<Minecraft>cast(this).objectMouseOver,
						world, ((BlockRayTraceResult) MixinHelper.<Minecraft>cast(this).objectMouseOver).getPos(), player);
				ci.cancel();
			}
		} else if (MixinHelper.<Minecraft>cast(this).objectMouseOver.getType() == RayTraceResult.Type.ENTITY) {
			if (((EntityRayTraceResult) MixinHelper.<Minecraft>cast(this).objectMouseOver).getEntity() instanceof CustomPickBlockBehavior) {
				((CustomPickBlockBehavior) ((EntityRayTraceResult) MixinHelper.<Minecraft>cast(this).objectMouseOver).getEntity()).getPickedResult(MixinHelper.<Minecraft>cast(this).objectMouseOver);
				ci.cancel();
			}
		}




	}
}
