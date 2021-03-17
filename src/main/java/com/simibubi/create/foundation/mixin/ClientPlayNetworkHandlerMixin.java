package com.simibubi.create.foundation.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.simibubi.create.foundation.networking.entity.ClientSpawnHandlerEntity;
import com.simibubi.create.foundation.networking.entity.ExtraSpawnDataEntity;
import com.simibubi.create.foundation.utility.extensions.EntitySpawnS2CPacketExtensions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	@Shadow
	private ClientWorld world;

	@ModifyVariable(at = @At(value = "JUMP", opcode = Opcodes.IFNULL, ordinal = 3, shift = Shift.BEFORE), method = "onEntitySpawn(Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;)V")
	public Entity replaceNullEntity(Entity entity, EntitySpawnS2CPacket packet) {
		if (entity == null) {
			EntityType<?> type = packet.getEntityTypeId();
			if (type != null) {
				entity = type.create(world);
				if (entity instanceof ClientSpawnHandlerEntity) {
					((ClientSpawnHandlerEntity) entity).onClientSpawn(packet);
				}
			}
		}
		return entity;
	}

	@Inject(at = @At(value = "INVOKE", target = "addEntity(ILnet/minecraft/entity/Entity;)V", shift = Shift.AFTER), method = "onEntitySpawn(Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;)V", locals = LocalCapture.CAPTURE_FAILHARD)
	public void afterAddEntity(EntitySpawnS2CPacket packet, CallbackInfo ci, double x, double y, double z, Entity entity) {
		if (entity instanceof ExtraSpawnDataEntity) {
			PacketByteBuf extraData = ((EntitySpawnS2CPacketExtensions) packet).getExtraDataBuf();
			if (extraData != null) {
				((ExtraSpawnDataEntity) entity).readSpawnData(extraData);
			}
		}
	}
}
