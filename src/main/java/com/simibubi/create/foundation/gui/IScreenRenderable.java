package com.simibubi.create.foundation.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface IScreenRenderable {

	@Environment(EnvType.CLIENT)
	void draw(MatrixStack ms, AbstractGui screen, int x, int y);

	@Environment(EnvType.CLIENT)
	default void draw(MatrixStack ms, int x, int y) {
		draw(ms, new Screen(new StringTextComponent("")) {
		}, x, y);
	}
}
