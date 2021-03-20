plugins {
	id("fabric-loom") version "0.6-SNAPSHOT"
	`maven-publish`
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8

	withSourcesJar()
}

base {
	archivesBaseName = properties["archives_base_name"] as String
}

version = properties["mod_version"]!!
group = properties["maven_group"]!!

repositories {
	maven("https://maven.fabricmc.net/") {
		name = "Fabric"
	}

	maven("https://raw.githubusercontent.com/Devan-Kerman/Devan-Repo/master/") {
		name = "HalfOf2"
	}

	maven("https://maven.terraformersmc.com/") {
		name = "TerraformersMC"
	}

	maven("https://maven.shedaniel.me/") {
		name = "Shedaniel"
	}

	maven("https://jitpack.io/") {
		name = "Jitpack"

		content {
			includeGroup("com.github.SuperCoder7979") // So Gradle doesn't spend time searching JitPack for other deps
		}
	}
}

dependencies {
	// We could also use properties["..."] here but this looks cleaner
	val minecraft_version: String by project
	val yarn_mappings: String by project
	val loader_version: String by project
	val fabric_version: String by project
	val arrp_version: String by project
	val databreaker_version: String by project
	val modmenu_version: String by project
	val rei_version: String by project
	val cloth_config_version: String by project

	minecraft("com.mojang", "minecraft", minecraft_version)

	/*
	 * TODO Mojmap
	 * mappings(minecraft.officialMojangMappings())
	 */
	mappings("net.fabricmc", "yarn", yarn_mappings, classifier = "v2")
	modImplementation("net.fabricmc", "fabric-loader", loader_version)

	// Fabric API
	modImplementation("net.fabricmc.fabric-api", "fabric-api", fabric_version)


	// Runtime resource generation
	modImplementation("net.devtech", "arrp", arrp_version)
	include("net.devtech", "arrp", arrp_version)

	// DataBreaker
	modRuntime("com.github.SuperCoder7979", "databreaker", databreaker_version) {
		exclude(module = "fabric-loader")
	}

	// ModMenu
	modImplementation("com.terraformersmc", "modmenu", modmenu_version)

	// REI
	modCompileOnly("me.shedaniel", "RoughlyEnoughItems-api", rei_version)
	modRuntime("me.shedaniel", "RoughlyEnoughItems", rei_version)

	// Cloth Config
	modApi("me.shedaniel.cloth", "cloth-config-fabric", cloth_config_version) {
		exclude(group = "net.fabricmc.fabric-api")
	}
}

loom {
	accessWidener("src/main/resources/create.accesswidener")
}

//////////// Gradle Properties ////////////
/**
 * Should be configured in `gradle.properties` like so:
 *
 * ```properties
 *     create.debug_logs=true
 *     create.mixin_export=true
 * ```
 */
@Suppress("UnstableApiUsage")
val lambda: () -> Unit = {
	val createPrefix = "create."
	val map = mutableMapOf<String, Project.() -> Unit>()

	map["debug_logs"] = {
		loom.runConfigs.configureEach {
			property("fabric.log.level", "debug")
		}
	}

	map["mixin_export"] = {
		loom.runConfigs.configureEach {
			property("mixin.debug.export", "true")
		}
	}

	map.forEach { (str, func) ->
		if (project.hasProperty(createPrefix + str) && (
				project.property(createPrefix + str) != null &&
					project.property(createPrefix + str) != false &&
					project.property(createPrefix + str) != "false"
				)) {
			project.func()
		}
	}
}; lambda()

tasks {
	processResources {
		inputs.property("version", project.version)

		filesMatching("fabric.mod.json") {
			expand("version" to project.version)
		}
	}

	withType<JavaCompile> {
		options.encoding = "UTF-8"
		if (JavaVersion.current().isJava9Compatible) {
			options.compilerArgs.addAll(listOf("--release", "8"))
		}
	}

	withType<Wrapper> {
		gradleVersion = "6.8.3"
		distributionType = Wrapper.DistributionType.BIN
	}

	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesBaseName}" }
		}
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			artifact(tasks.remapJar) {
				builtBy(tasks.remapJar)
			}

			artifact(tasks["sourcesJar"]) {
				builtBy(tasks.remapSourcesJar)
			}
		}
	}
}
