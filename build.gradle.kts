buildscript {
	repositories {
		maven("https://jitpack.io/")
	}

	dependencies {
		classpath("com.github.PepperCode1", "mcp-tiny", "fca1a43007b0ed3772610165843b2b76414e8b01")
	}
}

plugins {
	id("fabric-loom") version "0.6-SNAPSHOT"
	checkstyle
	`maven-publish`
}

java {
	withSourcesJar()
}

base {
	archivesBaseName = properties["archives_base_name"] as String
}

version = properties["mod_version"]!!
group = properties["maven_group"]!!

allprojects {
	apply(plugin = "java")

	java {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8

		withSourcesJar()
	}

	tasks.withType<JavaCompile> {
		options.encoding = "UTF-8"
		if (JavaVersion.current().isJava9Compatible) {
			options.release.set(8)
		}
	}
}

//

interface HasAnt { // Intersection and union types when
	val ant: AntBuilder

	companion object {
		fun from(it: Project) = object : HasAnt {
			override val ant get() = it.ant
		}

		fun from(it: Task) = object : HasAnt {
			override val ant get() = it.ant
		}
	}
}

val convertAndMoveMappings: HasAnt.(String, String) -> Unit = { mcpVer, mcpMcVer ->
	if (file(".gradle/mappings").listFiles() == null) mkdir(file(".gradle/mappings"))
	file(".gradle/mappings").listFiles().forEach {
		if (it.name.startsWith(".marker.")) project.delete(it)
	}

	me.shedaniel.mcptiny.main(arrayOf(mcpMcVer, mcpVer))
	ant.withGroovyBuilder {
		"move"("file" to "output.jar", "todir" to "$projectDir/.gradle/mappings")
	}
	file(".gradle/mappings/.marker.${mcpMcVer}__$mcpVer").createNewFile()
}

val createMappings by tasks.registering(Task::class) {
	inputs.property("mcpver", properties["mcp_mappings"])
	inputs.property("mcpmcver", properties["mcp_minecraft_version"])

	outputs.file(file(".gradle/mappings/output.jar"))
	outputs.file(file(".gradle/mappings/.marker.${inputs.properties["mcpmcver"] as String}__${inputs.properties["mcpver"] as String}"))

	doFirst {
		HasAnt.from(this).convertAndMoveMappings(inputs.properties["mcpver"] as String, inputs.properties["mcpmcver"] as String)
	}
}

val cleanMappings by tasks.creating(Delete::class) {
	delete(project.file("linkie-cache"))
	delete(project.file(".gradle/mappings"))
}
tasks["clean"].dependsOn(cleanMappings)

//

val setupBasicFabric: Project.() -> Unit = {
	apply(plugin = "fabric-loom")

	dependencies {
		// We could also use properties["..."] here but this looks cleaner
		val minecraft_version: String by rootProject
		val loader_version: String by rootProject
		val fabric_version: String by rootProject

		minecraft("com.mojang", "minecraft", minecraft_version)

		modImplementation("net.fabricmc", "fabric-loader", loader_version)

		// Fabric API
		modImplementation("net.fabricmc.fabric-api", "fabric-api", fabric_version)
	}

	val version = this.version
	tasks {
		processResources {
			inputs.property("version", version)

			filesMatching("fabric.mod.json") {
				expand("version" to version)
			}
		}
	}
}

val setupMCP: Project.() -> Unit = {
	dependencies {
		val mcp_mappings: String by rootProject
		val mcp_minecraft_version: String by rootProject

		fun setupMappings(p: Project = rootProject) {
			if (p.file(".gradle/mappings/.marker.${mcp_minecraft_version}__$mcp_mappings").exists() &&
				p.file(".gradle/mappings/output.jar").exists()) {
				return
			}

			HasAnt.from(p).convertAndMoveMappings(mcp_mappings, mcp_minecraft_version)
		}
		setupMappings()
		mappings(fileTree("dir" to "${rootProject.projectDir}/.gradle/mappings", "include" to "output.jar"))
	}
}

val setupYarn: Project.() -> Unit = {
	dependencies {
		val yarn_mappings: String by rootProject

		mappings("net.fabricmc", "yarn", yarn_mappings, classifier = "v2")
	}
}

val setupMojmap: Project.() -> Unit = {
	dependencies {
		mappings(minecraft.officialMojangMappings())
	}
}

val setupCheckstyle: Project.() -> Unit = {
	apply(plugin = "checkstyle")

	dependencies {
		// Checkstyle
		checkstyle(project(":Style-Checks"))
	}

	checkstyle {
		toolVersion = rootProject.properties["checkstyle_version"] as String
		configFile = rootProject.file("checkstyle/checkstyle.xml")
	}
}

//

project.setupBasicFabric()
project.setupMCP()
project.setupCheckstyle()

project(":Create-Refabricated-Lib").setupBasicFabric()
project(":Create-Refabricated-Lib").setupYarn()
project(":Create-Refabricated-Lib").setupCheckstyle()

//

repositories {
	maven("https://maven.fabricmc.net/") {
		name = "Fabric"
	}

	maven("https://mod-buildcraft.com/maven/") {
		name = "BuildCraft"
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
			// So Gradle doesn't spend time searching JitPack for other deps
			includeGroup("com.github.PepperCode1")
			includeGroup("com.github.SuperCoder7979")
		}
	}
}

dependencies {
	val registrate_version: String by project
	val lba_version: String by project
	val arrp_version: String by project
	val cloth_config_version: String by project
	val modmenu_version: String by project
	val rei_version: String by project

	val databreaker_version: String by project

	implementation(project(":Create-Refabricated-Lib"))
	include(project(":Create-Refabricated-Lib"))

	// Registrate
	modImplementation("com.github.PepperCode1", "Registrate-Fabric", registrate_version)
	include("com.github.PepperCode1", "Registrate-Fabric", registrate_version)

	// LibBlockAttributes
	modImplementation("alexiil.mc.lib", "libblockattributes-all", lba_version)
	//modImplementation("alexiil.mc.lib", "libblockattributes-items", lba_version)
	//modImplementation("alexiil.mc.lib", "libblockattributes-fluids", lba_version)

	// Runtime resource generation
	modImplementation("net.devtech", "arrp", arrp_version)
	include("net.devtech", "arrp", arrp_version)

	// Cloth Config
	modApi("me.shedaniel.cloth", "cloth-config-fabric", cloth_config_version) {
		exclude(group = "net.fabricmc.fabric-api")
	}

	// ModMenu
	modImplementation("com.terraformersmc", "modmenu", modmenu_version)

	// REI
	modCompileOnly("me.shedaniel", "RoughlyEnoughItems-api", rei_version)
	modRuntime("me.shedaniel", "RoughlyEnoughItems", rei_version)

	// DataBreaker
	modRuntime("com.github.SuperCoder7979", "databreaker", databreaker_version) {
		exclude(module = "fabric-loader")
	}
}

loom.accessWidener("src/main/resources/create.accesswidener")

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
	withType<Wrapper> {
		gradleVersion = "6.8.3"
		distributionType = Wrapper.DistributionType.BIN
	}

	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesBaseName}" }
		}
	}

	// Prevents build from running check. This is unnecessary as we can just run assemble.
	/*
	 *  build {
	 *		val newDependsOn = dependsOn.filter {
	 *			it.toString() != "check" && it.toString() != "checkstyleMain"
	 *		}
	 *		setDependsOn(newDependsOn)
	 * }
	 */
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
