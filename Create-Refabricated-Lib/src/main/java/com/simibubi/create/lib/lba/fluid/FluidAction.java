package com.simibubi.create.lib.lba.fluid;

import alexiil.mc.lib.attributes.Simulation;

public enum FluidAction {
	SIMULATE,
	EXECUTE;

	public boolean execute() {
		return this == EXECUTE;
	}

	public boolean simulate() {
		return this == SIMULATE;
	}

	public Simulation toSimulation() {
		return execute() ? Simulation.ACTION : Simulation.SIMULATE;
	}

	public static FluidAction fromSimulation(Simulation simulation) {
		return simulation.isAction() ? EXECUTE : SIMULATE;
	}
}
