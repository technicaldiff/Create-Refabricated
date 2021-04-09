package com.simibubi.create.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.lib.entity.ExtraSpawnDataEntity;
import com.simibubi.create.lib.extensions.EntitySpawnS2CPacketExtensions;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;

@Mixin(EntitySpawnS2CPacket.class)
public abstract class EntitySpawnS2CPacketMixin implements EntitySpawnS2CPacketExtensions {
	private PacketByteBuf create$extraDataBuf;

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/entity/Entity;I)V")
	public void create$onEntityCtor(Entity entity, int entityData, CallbackInfo ci) {
		create$setExtraData(entity);
	}

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/EntityType;ILnet/minecraft/util/math/BlockPos;)V")
	public void create$onEntityCtor(Entity entity, EntityType<?> entityType, int data, BlockPos pos, CallbackInfo ci) {
		create$setExtraData(entity);
	}

	private void create$setExtraData(Entity entity) {
		if (entity instanceof ExtraSpawnDataEntity) {
			create$extraDataBuf = new PacketByteBuf(Unpooled.buffer());
			((ExtraSpawnDataEntity) entity).writeSpawnData(create$extraDataBuf);
		}
	}

	@Inject(at = @At("TAIL"), method = "write(Lnet/minecraft/network/PacketByteBuf;)V")
	public void create$onTailWrite(PacketByteBuf buf, CallbackInfo ci) {
		if (create$extraDataBuf != null) {
			buf.writeBytes(create$extraDataBuf);
		}
	}

	@Inject(at = @At("TAIL"), method = "read(Lnet/minecraft/network/PacketByteBuf;)V")
	public void create$onTailRead(PacketByteBuf buf, CallbackInfo ci) {
		int readable = buf.readableBytes();
		if (readable != 0) {
			this.create$extraDataBuf = new PacketByteBuf(buf.readBytes(readable));
		}
	}

	@Inject(at = @At("TAIL"), method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V")
	public void create$onTailApply(ClientPlayPacketListener listener, CallbackInfo ci) {
		if (create$extraDataBuf != null) {
			create$extraDataBuf.release();
		}
	}

	@Override
	public PacketByteBuf create$getExtraDataBuf() {
		return create$extraDataBuf;
	}
}
