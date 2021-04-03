pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }
}

rootProject.name = "Create-Refabricated"
include("Create-Refabricated-Lib")
include("Style-Checks")
