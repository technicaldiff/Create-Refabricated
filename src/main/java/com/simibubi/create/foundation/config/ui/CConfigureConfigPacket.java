package com.simibubi.create.foundation.config.ui;

import java.util.function.Supplier;

import com.simibubi.create.foundation.command.SConfigureConfigPacket;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.networking.SimplePacketBase;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CConfigureConfigPacket<T> implements C2SPacket {

	private String modID;
	private String path;
	private String value;

	protected CConfigureConfigPacket() {}

	public CConfigureConfigPacket(String modID, String path, T value) {
		this.modID = Objects.requireNonNull(modID);
		this.path = path;
		this.value = serialize(value);
	}

	@Override
	public void read(PacketBuffer buffer) {
		this.modID = buffer.readString(32767);
		this.path = buffer.readString(32767);
		this.value = buffer.readString(32767);
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeString(modID);
		buffer.writeString(path);
		buffer.writeString(value);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity sender, ServerPlayNetHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		context.get().enqueueWork(() -> {
			try {
				ServerPlayerEntity sender = context.get().getSender();
				if (sender == null || !sender.hasPermissionLevel(2))
					return;

				ForgeConfigSpec spec = ConfigHelper.findConfigSpecFor(ModConfig.Type.SERVER, modID);
				ForgeConfigSpec.ValueSpec valueSpec = spec.getRaw(path);
				ForgeConfigSpec.ConfigValue<T> configValue = spec.getValues().get(path);

				T v = (T) deserialize(configValue.get(), value);
				if (!valueSpec.test(v))
					return;

				configValue.set(v);
			} catch (Exception e) {
				Create.LOGGER.warn("Unable to handle ConfigureConfig Packet. ", e);
			}
		});

		context.get().setPacketHandled(true);
	}

	public String serialize(T value) {
		if (value instanceof Boolean)
			return Boolean.toString((Boolean) value);
		if (value instanceof Enum<?>)
			return ((Enum<?>) value).name();
		if (value instanceof Integer)
			return Integer.toString((Integer) value);
		if (value instanceof Float)
			return Float.toString((Float) value);
		if (value instanceof Double)
			return Double.toString((Double) value);

		throw new IllegalArgumentException("unknown type " + value + ": " + value.getClass().getSimpleName());
	}

	public static Object deserialize(Object type, String sValue) {
		if (type instanceof Boolean)
			return Boolean.parseBoolean(sValue);
		if (type instanceof Enum<?>)
			return Enum.valueOf(((Enum<?>) type).getClass(), sValue);
		if (type instanceof Integer)
			return Integer.parseInt(sValue);
		if (type instanceof Float)
			return Float.parseFloat(sValue);
		if (type instanceof Double)
			return Double.parseDouble(sValue);

		throw new IllegalArgumentException("unknown type " + type + ": " + type.getClass().getSimpleName());
	}
}
