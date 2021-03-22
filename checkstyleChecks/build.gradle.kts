plugins {
    java
}

version = properties["version"]!!
group = "${rootProject.properties["maven_group"]}.${rootProject.properties["archives_base_name"]}"

base {
    archivesBaseName = properties["archives_base_name"] as String
}

repositories {
    mavenCentral()
}

dependencies {
    val checkstyle_version: String by rootProject
    implementation("com.puppycrawl.tools", "checkstyle", checkstyle_version)
}
