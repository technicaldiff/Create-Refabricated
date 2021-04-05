plugins {
	java
}

repositories {
	mavenCentral()
	maven("https://maven.shedaniel.me/")
	maven("https://maven.fabricmc.net/")
	maven("https://jitpack.io/")
}

dependencies {
	implementation("com.github.PepperCode1", "mcp-tiny", "fca1a43007b0ed3772610165843b2b76414e8b01")
	implementation("net.fabricmc", "fabric-loom", "0.6-SNAPSHOT")
}
