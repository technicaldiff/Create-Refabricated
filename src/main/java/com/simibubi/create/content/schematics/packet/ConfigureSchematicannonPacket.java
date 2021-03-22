package com.simibubi.create.content.schematics.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;

public class ConfigureSchematicannonPacket implements C2SPacket {

	public static enum Option {
		DONT_REPLACE, REPLACE_SOLID, REPLACE_ANY, REPLACE_EMPTY, SKIP_MISSING, SKIP_TILES, PLAY, PAUSE, STOP;
	}

	private Option option;
	private boolean set;

	public ConfigureSchematicannonPacket(Option option, boolean set) {
		this.option = option;
		this.set = set;
	}

	@Override
	public void read(PacketByteBuf buffer) {
		option = buffer.readEnumConstant(Option.class);
		set = buffer.readBoolean();
	}

	@Override
	public void write(PacketByteBuf buffer) {
		buffer.writeEnumConstant(option);
		buffer.writeBoolean(set);
	}

	@Override
	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, SimpleChannel.ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null /*|| !(player.currentScreenHandler instanceof SchematicannonContainer)*/)
				return;

			/*SchematicannonBlockEntity te = ((SchematicannonContainer) player.currentScreenHandler).getTileEntity();
			switch (option) {
				case DONT_REPLACE:
				case REPLACE_ANY:
				case REPLACE_EMPTY:
				case REPLACE_SOLID:
					te.replaceMode = option.ordinal();
					break;
				case SKIP_MISSING:
					te.skipMissing = set;
					break;
				case SKIP_TILES:
					te.replaceTileEntities = set;
					break;

				case PLAY:
					te.state = SchematicannonBlockEntity.State.RUNNING;
					te.statusMsg = "running";
					break;
				case PAUSE:
					te.state = SchematicannonBlockEntity.State.PAUSED;
					te.statusMsg = "paused";
					break;
				case STOP:
					te.state = SchematicannonBlockEntity.State.STOPPED;
					te.statusMsg = "stopped";
					break;
				default:
					break;
			}

			te.sendUpdate = true;*/
		});
	}

}
