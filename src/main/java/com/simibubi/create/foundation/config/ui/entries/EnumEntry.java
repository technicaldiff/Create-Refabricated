package com.simibubi.create.foundation.config.ui.entries;

import java.util.Locale;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.BoxElement;
import com.simibubi.create.foundation.gui.DelegatedStencilElement;
import com.simibubi.create.foundation.gui.TextStencilElement;
import com.simibubi.create.foundation.gui.Theme;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.widgets.BoxWidget;
import com.simibubi.create.lib.config.ConfigValue;

import net.minecraft.client.Minecraft;

public class EnumEntry<T> extends ValueEntry<T> {

	protected static final int cycleWidth = 34;

	protected TextStencilElement valueText;
	protected BoxWidget cycleLeft;
	protected BoxWidget cycleRight;

	public EnumEntry(String label, ConfigValue<T> value) {
		super(label, value);

		valueText = new TextStencilElement(Minecraft.getInstance().fontRenderer, "YEP").centered(true, true);
		valueText.withElementRenderer((ms, width, height, alpha) -> UIRenderHelper.angledGradient(ms, 0, 0, height / 2,
			height, width, Theme.p(Theme.Key.TEXT)));

		DelegatedStencilElement l = AllIcons.I_CONFIG_PREV.asStencil();
		cycleLeft = new BoxWidget(0, 0, cycleWidth + 8, 16).showingElement(l)
			.withCallback(() -> cycleValue(-1));
		l.withElementRenderer(BoxWidget.gradientFactory.apply(cycleLeft));

		DelegatedStencilElement r = AllIcons.I_CONFIG_NEXT.asStencil();
		cycleRight = new BoxWidget(0, 0, cycleWidth + 8, 16).showingElement(r)
			.withCallback(() -> cycleValue(1));
		r.at(cycleWidth - 8, 0);
		r.withElementRenderer(BoxWidget.gradientFactory.apply(cycleRight));

		listeners.add(cycleLeft);
		listeners.add(cycleRight);

		onReset();
	}

	protected void cycleValue(int direction) {
		Enum<?> e = (Enum<?>) getValue();
		Enum<?>[] options = e.getDeclaringClass()
			.getEnumConstants();
		e = options[Math.floorMod(e.ordinal() + direction, options.length)];
		setValue((T) e);
		bumpCog(direction * 15f);
	}

	@Override
	protected void setEditable(boolean b) {
		super.setEditable(b);
		cycleLeft.active = b;
		cycleLeft.animateGradientFromState();
		cycleRight.active = b;
		cycleRight.animateGradientFromState();
	}

	@Override
	public void tick() {
		super.tick();
		cycleLeft.tick();
		cycleRight.tick();
	}

	@Override
	public void render(MatrixStack ms, int index, int y, int x, int width, int height, int mouseX, int mouseY,
		boolean p_230432_9_, float partialTicks) {
		super.render(ms, index, y, x, width, height, mouseX, mouseY, p_230432_9_, partialTicks);

		cycleLeft.x = x + getLabelWidth(width) + 4;
		cycleLeft.y = y + 10;
		cycleLeft.render(ms, mouseX, mouseY, partialTicks);

		valueText.at(cycleLeft.x + cycleWidth - 8, y + 10, 200)
				.withBounds(width - getLabelWidth(width) - 2 * cycleWidth - resetWidth - 4, 16)
				.render(ms);

		cycleRight.x = x + width - cycleWidth * 2 - resetWidth + 10;
		cycleRight.y = y + 10;
		cycleRight.render(ms, mouseX, mouseY, partialTicks);

		new BoxElement()
				.withBackground(0)
				.flatBorder(0)
				.withBounds(10, 10)
				.at(cycleLeft.x + cycleWidth + 4, cycleLeft.y + 3)
				.render(ms);
	}

	@Override
	public void onValueChange(T newValue) {
		super.onValueChange(newValue);
		valueText.withText(((Enum) newValue).name()
			.substring(0, 1)
			+ ((Enum) newValue).name()
				.substring(1)
				.toLowerCase(Locale.ROOT));
	}
}
