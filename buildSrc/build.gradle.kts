plugins {
	`java`
}

repositories {
	jcenter()
	mavenCentral()
	maven("https://maven.fabricmc.net/")
	maven("https://server.bbkr.space/artifactory/libs-release/") {
		name = "Cotton"
	}
	maven("https://jitpack.io/")
	maven("https://maven.shedaniel.me/")
}

dependencies {
	implementation("net.fabricmc", "fabric-loom", "0.7-SNAPSHOT")
	implementation("io.github.juuxel", "loom-quiltflower", "1.1.0")
	implementation("com.github.PepperCode1", "mcp-tiny", "1bde92310cd80f0d834403d40219a2a06be431da")
}
