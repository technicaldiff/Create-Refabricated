package com.simibubi.create.lib.mixin;

import io.netty.buffer.Unpooled;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.extensions.EntitySpawnS2CPacketExtensions;
import com.simibubi.create.lib.networking.entity.ExtraSpawnDataEntity;

@Mixin(EntitySpawnS2CPacket.class)
public class EntitySpawnS2CPacketMixin implements EntitySpawnS2CPacketExtensions {
	private PacketByteBuf extraDataBuf;

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/entity/Entity;I)V")
	public void onEntityCtor(Entity entity, int entityData, CallbackInfo ci) {
		setExtraData(entity);
	}

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/EntityType;ILnet/minecraft/util/math/BlockPos;)V")
	public void onEntityCtor(Entity entity, EntityType<?> entityType, int data, BlockPos pos, CallbackInfo ci) {
		setExtraData(entity);
	}

	private void setExtraData(Entity entity) {
		if (entity instanceof ExtraSpawnDataEntity) {
			extraDataBuf = new PacketByteBuf(Unpooled.buffer());
			((ExtraSpawnDataEntity) entity).writeSpawnData(extraDataBuf);
		}
	}

	@Inject(at = @At("TAIL"), method = "write(Lnet/minecraft/network/PacketByteBuf;)V")
	public void onTailWrite(PacketByteBuf buf, CallbackInfo ci) {
		if (extraDataBuf != null) {
			buf.writeBytes(extraDataBuf);
		}
	}

	@Inject(at = @At("TAIL"), method = "read(Lnet/minecraft/network/PacketByteBuf;)V")
	public void onTailRead(PacketByteBuf buf, CallbackInfo ci) {
		int readable = buf.readableBytes();
		if (readable != 0) {
			this.extraDataBuf = new PacketByteBuf(buf.readBytes(readable));
		}
	}

	@Inject(at = @At("TAIL"), method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V")
	public void onTailApply(ClientPlayPacketListener listener, CallbackInfo ci) {
		if (extraDataBuf != null) {
			extraDataBuf.release();
		}
	}

	@Override
	public PacketByteBuf getExtraDataBuf() {
		return extraDataBuf;
	}
}
