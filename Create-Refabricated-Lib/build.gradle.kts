version = properties["version"]!!
group = "${rootProject.properties["maven_group"]}.${rootProject.properties["archives_base_name"]}"

base {
	archivesBaseName = properties["archives_base_name"] as String
}

dependencies {
	// Javax Annotations
	implementation("com.google.code.findbugs", "jsr305", "3.0.2")
}

loom.accessWidener("src/main/resources/create_lib.accesswidener")
