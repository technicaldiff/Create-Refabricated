version = properties["version"]!!
group = "${rootProject.properties["maven_group"]}.${rootProject.properties["archives_base_name"]}"

base {
    archivesBaseName = properties["archives_base_name"] as String
}
