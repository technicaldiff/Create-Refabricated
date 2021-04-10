package com.simibubi.create.lib.mixin.accessor;

import java.util.Set;
import net.minecraft.block.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WoodType.class)
public interface WoodTypeAccessor {
	@Accessor("VALUES")
	static Set<WoodType> create$VALUES() {
		throw new AssertionError();
	}
}
