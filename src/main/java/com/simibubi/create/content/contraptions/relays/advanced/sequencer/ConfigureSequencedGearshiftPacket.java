package com.simibubi.create.content.contraptions.relays.advanced.sequencer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

import com.simibubi.create.foundation.networking.TileEntityConfigurationPacket;
import com.simibubi.create.lib.utility.Constants.NBT;

public class ConfigureSequencedGearshiftPacket extends TileEntityConfigurationPacket<SequencedGearshiftTileEntity> {

	private ListNBT instructions;

	public ConfigureSequencedGearshiftPacket(BlockPos pos, ListNBT instructions) {
		super(pos);
		this.instructions = instructions;
	}

	public ConfigureSequencedGearshiftPacket(PacketBuffer buffer) {
		super(buffer);
	}

	@Override
	protected void readSettings(PacketBuffer buffer) {
		instructions = buffer.readCompoundTag().getList("data", NBT.TAG_COMPOUND);
	}

	@Override
	protected void writeSettings(PacketBuffer buffer) {
		CompoundNBT tag = new CompoundNBT();
		tag.put("data", instructions);
		buffer.writeCompoundTag(tag);
	}

	@Override
	protected void applySettings(SequencedGearshiftTileEntity te) {
		te.run(-1);
		te.instructions = Instruction.deserializeAll(instructions);
		te.sendData();
	}

}
