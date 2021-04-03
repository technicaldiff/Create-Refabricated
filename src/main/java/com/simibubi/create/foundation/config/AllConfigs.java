// PORTED CREATE SOURCE

package com.simibubi.create.foundation.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

@Config(name = "create")
public class AllConfigs implements ConfigData {
	public static void register() {
		try {
			AutoConfig.register(AllConfigs.class, Toml4jConfigSerializer::new).getConfig().validatePostLoad();
		} catch (ValidationException e) {
			throw new RuntimeException(e);
		}
	}

	// use this to use the config
	// ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

	/* see CServer (old)
	static String recipes = "Packmakers' control panel for internal recipe compat";
	static String schematics = "Everything related to Schematic tools";
	static String kinetics = "Parameters and abilities of Create's kinetic mechanisms";
	static String fluids = "Create's liquid manipulation tools";
	static String logistics = "Tweaks for logistical components";
	static String curiosities = "Gadgets and other Shenanigans added by Create";
	static String infrastructure = "The Backbone of Create";
	 */

	// creating collapsible groups
	@CollapsibleObject
	@Tooltip
	public CClient client = new CClient();

	@CollapsibleObject
	public CCommon common = new CCommon();

	@CollapsibleObject
	public CCuriosities curiosities = new CCuriosities();

	@CollapsibleObject
	public CFluids fluids = new CFluids();

	@CollapsibleObject
	public CKinetics kinetics = new CKinetics();

	@CollapsibleObject
	public CLogistics logistics = new CLogistics();

	@CollapsibleObject
	public CRecipes recipes = new CRecipes();

	@CollapsibleObject
	public CSchematics schematics = new CSchematics();

	@CollapsibleObject
	public CServer server = new CServer();

	@CollapsibleObject
	@Tooltip(count = 2)
	public CStress stress = new CStress();

	@CollapsibleObject
	public CWorldGen worldGen = new CWorldGen();

	@Override
	public void validatePostLoad() throws ValidationException {
		client.validate();
		curiosities.validate();
		fluids.validate();
		kinetics.validate();
		logistics.validate();
		recipes.validate();
		schematics.validate();
		server.validate();
		worldGen.validate();
	}
}
