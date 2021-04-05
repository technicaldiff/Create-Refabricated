package com.simibubi.create.grdl.task;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class CleanMappingsTask extends DefaultTask {
	@Inject
	public CleanMappingsTask() {}

	@TaskAction
	public void doTask() {
		getProject().delete(getProject().getRootProject().file("linkie-cache"));
	}
}
