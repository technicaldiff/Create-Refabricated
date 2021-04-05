package com.simibubi.create.grdl;

import org.gradle.api.AntBuilder;
import org.gradle.api.Project;
import org.gradle.api.Task;

@FunctionalInterface
public interface HasAnt {
	AntBuilder getAnt();

	static HasAnt from(Task t) {
		return t::getAnt;
	}

	static HasAnt from(Project p) {
		return p::getAnt;
	}
}
