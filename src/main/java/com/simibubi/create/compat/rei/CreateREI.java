package com.simibubi.create.compat.rei;

import com.simibubi.create.Create;

import me.shedaniel.rei.api.plugins.REIPluginV0;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CreateREI implements REIPluginV0 {

	private static final Identifier ID = new Identifier(Create.ID, "jei_plugin");

	@Override
	public Identifier getPluginIdentifier() {
		return ID;
	}

}
