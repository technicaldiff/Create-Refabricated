package com.simibubi.create.foundation.config;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.lib.utility.ConfigValue;

import dev.inkwell.conrad.api.value.data.SaveType;
import dev.inkwell.conrad.api.value.serialization.ConfigSerializer;
import dev.inkwell.conrad.api.value.serialization.FlatOwenSerializer;
import dev.inkwell.owen.OwenElement;
import dev.inkwell.vivian.api.builders.CategoryBuilder;

public class CServer extends ConfigBase {

	@Override
	public @NotNull SaveType getSaveType() {
		return SaveType.LEVEL;
	}

	@Override
	public @NotNull ConfigSerializer<OwenElement> getSerializer() {
		return FlatOwenSerializer.INSTANCE;
	}

	public CategoryBuilder infrastructure = group(0, "infrastructure", null, Comments.infrastructure);
	public ConfigValue<Integer> tickrateSyncTimer =
		i(20, 5, "tickrateSyncTimer", infrastructure, "[in Ticks]", Comments.tickrateSyncTimer, Comments.tickrateSyncTimer2);

	public CRecipes recipes = new CRecipes();
	public CKinetics kinetics = new CKinetics();
	public CFluids fluids = new CFluids();
	public CLogistics logistics = new CLogistics();
	public CSchematics schematics = new CSchematics();
	public CCuriosities curiosities = new CCuriosities();

	@Override
	public String getName() {
		return "server";
	}

	public static class Comments {
		static String recipes = "Packmakers' control panel for internal recipe compat";
		static String schematics = "Everything related to Schematic tools";
		static String kinetics = "Parameters and abilities of Create's kinetic mechanisms";
		static String fluids = "Create's liquid manipulation tools";
		static String logistics = "Tweaks for logistical components";
		static String curiosities = "Gadgets and other Shenanigans added by Create";
		static String infrastructure = "The Backbone of Create";
		static String tickrateSyncTimer =
			"The amount of time a server waits before sending out tickrate synchronization packets.";
		static String tickrateSyncTimer2 = "These packets help animations to be more accurate when tps is below 20.";
	}

}
