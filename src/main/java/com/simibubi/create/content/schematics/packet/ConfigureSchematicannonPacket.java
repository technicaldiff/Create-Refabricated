package com.simibubi.create.content.schematics.packet;

import com.simibubi.create.content.schematics.block.SchematicannonContainer;
import com.simibubi.create.content.schematics.block.SchematicannonTileEntity;
import com.simibubi.create.content.schematics.block.SchematicannonTileEntity.State;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;

public class ConfigureSchematicannonPacket implements C2SPacket {

	public static enum Option {
		DONT_REPLACE, REPLACE_SOLID, REPLACE_ANY, REPLACE_EMPTY, SKIP_MISSING, SKIP_TILES, PLAY, PAUSE, STOP;
	}

	private Option option;
	private boolean set;

	protected ConfigureSchematicannonPacket() {}

	public ConfigureSchematicannonPacket(Option option, boolean set) {
		this.option = option;
		this.set = set;
	}

	public void read(PacketBuffer buffer) {
		option = buffer.readEnumValue(Option.class);
		set = buffer.readBoolean();
	}

	public void write(PacketBuffer buffer) {
		buffer.writeEnumValue(option);
		buffer.writeBoolean(set);
	}

	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null || !(player.openContainer instanceof SchematicannonContainer))
				return;

			SchematicannonTileEntity te = ((SchematicannonContainer) player.openContainer).getTileEntity();
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
				te.state = State.RUNNING;
				te.statusMsg = "running";
				break;
			case PAUSE:
				te.state = State.PAUSED;
				te.statusMsg = "paused";
				break;
			case STOP:
				te.state = State.STOPPED;
				te.statusMsg = "stopped";
				break;
			default:
				break;
			}

			te.sendUpdate = true;
		});
	}

}
