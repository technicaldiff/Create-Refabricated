package com.simibubi.create.lib.utility;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

public class SpecialModelUtil {
	public static Set<ResourceLocation> specialModels = new HashSet<>();
	public static void addSpecialModel(ResourceLocation location) {
		specialModels.add(location);
	}
}
