package com.simibubi.create.foundation.config;

import com.simibubi.create.lib.config.Config;
import com.simibubi.create.lib.config.ConfigGroup;
import com.simibubi.create.lib.config.Configs;

public class CServer extends ConfigBase {

	public ConfigGroup infrastructure = group(0, "infrastructure", Comments.infrastructure);
	public ConfigInt tickrateSyncTimer =
		i(20, 5, "tickrateSyncTimer", "[in Ticks]", Comments.tickrateSyncTimer, Comments.tickrateSyncTimer2);

	public boolean register = register();

	public CRecipes recipes;
	public CKinetics kinetics;
	public CFluids fluids;
	public CLogistics logistics;
	public CSchematics schematics;
	public CCuriosities curiosities;

	public boolean register() {
		recipes = nested(0, CRecipes::new, Comments.recipes);
		initGroups(recipes.getConfig());
		kinetics = nested(0, CKinetics::new, Comments.kinetics);
		initGroups(kinetics.getConfig());
		fluids = nested(0, CFluids::new, Comments.fluids);
		initGroups(fluids.getConfig());
		logistics = nested(0, CLogistics::new, Comments.logistics);
		initGroups(logistics.getConfig());
		schematics = nested(0, CSchematics::new, Comments.schematics);
		initGroups(schematics.getConfig());
		curiosities = nested(0, CCuriosities::new, Comments.curiosities);
		initGroups(curiosities.getConfig());
		return true;
	}

	public Config config = new Config(getName());
	@Override
	public Config getConfig() {
		return config;
	}

	@Override
	public String getName() {
		return "server";
	}

	public static class Comments {
		public static String recipes = "Packmakers' control panel for internal recipe compat";
		public static String schematics = "Everything related to Schematic tools";
		public static String kinetics = "Parameters and abilities of Create's kinetic mechanisms";
		public static String fluids = "Create's liquid manipulation tools";
		public static String logistics = "Tweaks for logistical components";
		public static String curiosities = "Gadgets and other Shenanigans added by Create";
		public static String infrastructure = "The Backbone of Create";
		public static String tickrateSyncTimer =
			"The amount of time a server waits before sending out tickrate synchronization packets.";
		public static String tickrateSyncTimer2 = "These packets help animations to be more accurate when tps is below 20.";
	}

}
