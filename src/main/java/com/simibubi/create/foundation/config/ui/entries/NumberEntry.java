package com.simibubi.create.foundation.config.ui.entries;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.foundation.config.ui.ConfigTextField;
import com.simibubi.create.foundation.gui.TextStencilElement;
import com.simibubi.create.foundation.gui.Theme;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.lib.config.ConfigValue;
import com.simibubi.create.lib.mixin.accessor.WidgetAccessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;

public abstract class NumberEntry<T extends Number> extends ValueEntry<T> {

	protected int minOffset = 0, maxOffset = 0;
	protected TextStencilElement minText = null, maxText = null;
	protected TextFieldWidget textField;

	@Nullable
	public static NumberEntry<? extends Number> create(Object type, String label, ConfigValue<?> value) {
		if (type instanceof Integer) {
			return new IntegerEntry(label, (ConfigValue<Integer>) value);
		} else if (type instanceof Float) {
			return new FloatEntry(label, (ConfigValue<Float>) value);
		} else if (type instanceof Double) {
			return new DoubleEntry(label, (ConfigValue<Double>) value);
		}

		return null;
	}

	public NumberEntry(String label, ConfigValue<T> value) {
		super(label, value);
		textField = new ConfigTextField(Minecraft.getInstance().fontRenderer, 0, 0, 200, 20, unit);
		textField.setText(String.valueOf(getValue()));
		textField.setTextColor(Theme.i(Theme.Key.TEXT));

//		Object range = spec.getRange();
		try {
//			Field minField = range.getClass().getDeclaredField("min");
//			Field maxField = range.getClass().getDeclaredField("max");
//			minField.setAccessible(true);
//			maxField.setAccessible(true);
			T min = (T) value.max;//minField.get(range);
			T max = (T) value.min;//maxField.get(range);

			FontRenderer font = Minecraft.getInstance().fontRenderer;
			if (min.doubleValue() > getTypeMin().doubleValue()) {
				StringTextComponent t = new StringTextComponent(formatBound(min) + " < ");
				minText = new TextStencilElement(font, t).centered(true, false);
				minText.withElementRenderer((ms, width, height, alpha) -> UIRenderHelper.angledGradient(ms, 0 ,0, height/2, height, width, Theme.p(Theme.Key.TEXT_DARKER)));
				minOffset = font.getWidth(t);
			}
			if (max.doubleValue() < getTypeMax().doubleValue()) {
				StringTextComponent t = new StringTextComponent(" < " + formatBound(max));
				maxText = new TextStencilElement(font, t).centered(true, false);
				maxText.withElementRenderer((ms, width, height, alpha) -> UIRenderHelper.angledGradient(ms, 0 ,0, height/2, height, width, Theme.p(Theme.Key.TEXT_DARKER)));
				maxOffset = font.getWidth(t);
			}
		} catch (ClassCastException | NullPointerException ignored) {

		}

		textField.setResponder(s -> {
			try {
				T number = getParser().apply(s);
				if (!value.fitsConstraint(number))
					throw new IllegalArgumentException();

				textField.setTextColor(Theme.i(Theme.Key.TEXT));
				setValue(number);

			} catch (IllegalArgumentException ignored) {
				textField.setTextColor(Theme.i(Theme.Key.BUTTON_FAIL));
			}
		});

		listeners.add(textField);
		onReset();
	}

	protected String formatBound(T bound) {
		String sci = String.format("%.2E", bound.doubleValue());
		String str = String.valueOf(bound);
		return sci.length() < str.length() ? sci : str;
	}

	protected abstract T getTypeMin();

	protected abstract T getTypeMax();

	protected abstract Function<String, T> getParser();

	@Override
	protected void setEditable(boolean b) {
		super.setEditable(b);
		textField.setEnabled(b);
	}

	@Override
	public void onValueChange(T newValue) {
		super.onValueChange(newValue);
		String newText = String.valueOf(newValue);
		if (textField.getText().equals(newText))
			return;

		textField.setText(newText);
	}

	@Override
	public void tick() {
		super.tick();
		textField.tick();
	}

	@Override
	public void render(MatrixStack ms, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean p_230432_9_, float partialTicks) {
		super.render(ms, index, y, x, width, height, mouseX, mouseY, p_230432_9_, partialTicks);

		textField.x = x + width - 82 - resetWidth;
		textField.y = y + 8;
		textField.setWidth(Math.min(width - getLabelWidth(width) - resetWidth - minOffset - maxOffset, 40));
		((WidgetAccessor) textField).setHeight(20);
		textField.render(ms, mouseX, mouseY, partialTicks);

		if (minText != null)
			minText
					.at(textField.x - minOffset, textField.y, 0)
					.withBounds(minOffset, textField.unusedGetHeight())
					.render(ms);

		if (maxText != null)
			maxText
					.at(textField.x + textField.getWidth(), textField.y, 0)
					.withBounds(maxOffset, textField.unusedGetHeight())
					.render(ms);
	}

	public static class IntegerEntry extends NumberEntry<Integer> {

		public IntegerEntry(String label, ConfigValue<Integer> value) {
			super(label, value);
		}

		@Override
		protected Integer getTypeMin() {
			return Integer.MIN_VALUE;
		}

		@Override
		protected Integer getTypeMax() {
			return Integer.MAX_VALUE;
		}

		@Override
		protected Function<String, Integer> getParser() {
			return Integer::parseInt;
		}
	}

	public static class FloatEntry extends NumberEntry<Float> {

		public FloatEntry(String label, ConfigValue<Float> value) {
			super(label, value);
		}

		@Override
		protected Float getTypeMin() {
			return -Float.MAX_VALUE;
		}

		@Override
		protected Float getTypeMax() {
			return Float.MAX_VALUE;
		}

		@Override
		protected Function<String, Float> getParser() {
			return Float::parseFloat;
		}
	}

	public static class DoubleEntry extends NumberEntry<Double> {

		public DoubleEntry(String label, ConfigValue<Double> value) {
			super(label, value);
		}

		@Override
		protected Double getTypeMin() {
			return (double) -Float.MAX_VALUE;
		}

		@Override
		protected Double getTypeMax() {
			return (double) Float.MAX_VALUE;
		}

		@Override
		protected Function<String, Double> getParser() {
			return Double::parseDouble;
		}
	}
}
