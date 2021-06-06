package com.simibubi.create.lib.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.Widget;

@Environment(EnvType.CLIENT)
@Mixin(Widget.class)
public interface WidgetAccessor {
	@Accessor("height")
	int getHeight();

	@Accessor("height")
	void setHeight(int height);
}
