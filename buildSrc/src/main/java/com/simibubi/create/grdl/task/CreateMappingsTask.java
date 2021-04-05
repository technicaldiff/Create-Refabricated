package com.simibubi.create.grdl.task;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import com.simibubi.create.grdl.HasAnt;
import com.simibubi.create.grdl.MappingsUtil;

public class CreateMappingsTask extends DefaultTask {
	@Inject
	public CreateMappingsTask() {
		final Project root = getProject().getRootProject();
		setGroup("create");

		getInputs().property("mcpver", root.getProperties().get("mcp_mappings"));
		getInputs().property("mcpmcver", root.getProperties().get("mcp_minecraft_version"));

		getOutputs().file(root.file(".gradle/mappings/output.jar"));
		getOutputs().file(root.file(
			".gradle/mappings/.marker." +
			getInputs().getProperties().get("mcpmcver") +
			"__" +
			getInputs().getProperties().get("mcpver")
		));
	}

	@TaskAction
	public void doTask() throws Throwable {
		new MappingsUtil(getProject()).convertAndMoveMappings(
			HasAnt.from(this),
			(String) getInputs().getProperties().get("mcpver"),
			(String) getInputs().getProperties().get("mcpmcver")
		);
	}
}
