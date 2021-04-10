package com.simibubi.create.content.schematics.packet;

import com.simibubi.create.content.schematics.SchematicProcessor;
import com.simibubi.create.content.schematics.item.SchematicItem;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel.ResponseTarget;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

public class SchematicPlacePacket implements C2SPacket {

	public ItemStack stack;

	protected SchematicPlacePacket() {}

	public SchematicPlacePacket(ItemStack stack) {
		this.stack = stack;
	}

	public void read(PacketBuffer buffer) {
		stack = buffer.readItemStack();
	}

	public void write(PacketBuffer buffer) {
		buffer.writeItemStack(stack);
	}

	public void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetHandler handler, ResponseTarget responseTarget) {
		server.execute(() -> {
			if (player == null)
				return;
			Template t = SchematicItem.loadSchematic(stack);
			PlacementSettings settings = SchematicItem.getSettings(stack);
			if (player.canUseCommandBlock())
				settings.func_215220_b(SchematicProcessor.INSTANCE); // remove processor
			settings.setIgnoreEntities(false);
			t.place(player.getServerWorld(), NBTUtil.readBlockPos(stack.getTag().getCompound("Anchor")),
					settings, player.getRNG());
		});
	}

}
