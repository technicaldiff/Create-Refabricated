package com.simibubi.create.grdl;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Project;

import net.fabricmc.loom.LoomGradleExtension;

public final class PropertiesHandler {
	private final org.gradle.api.Project p;

	public PropertiesHandler(Project p) {
		this.p = p;
	}

	private LoomGradleExtension getExt() {
		return p.getExtensions().getByType(LoomGradleExtension.class);
	}

	/**
	 * Should be configured in {@code gradle.properties} like so:
	 *
	 * <blockquote><pre>
	 * create.debug_logs=true
	 * create.mixin_export=true
	 * </pre></blockquote>
	 */
	@SuppressWarnings("UnstableApiUsage")
	public void setupProperties() {
		p.getExtensions().getByType(LoomGradleExtension.class);

		final String createPrefix = "create.";
		final Map<String, Runnable> map = new HashMap<>();

		map.put("debug_logs", () -> getExt().getRunConfigs().configureEach(settings -> settings.property("fabric.log.level", "debug")));
		map.put("mixin_export", () -> getExt().getRunConfigs().configureEach(settings -> settings.property("mixin.debug.export", "true")));

		map.forEach((str, act) -> {
			if (
				p.hasProperty(createPrefix + str) && (
					p.property(createPrefix + str) != null &&
						Boolean.parseBoolean((String) p.property(createPrefix + str))
				)
			) {
				act.run();
			}
		});
	}
}
