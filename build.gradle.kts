plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "settingdust.terra.hoconlanguage"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.dfsek.terra:api:6.4.3-BETA+ab60f14ff")
    compileOnly("com.dfsek.terra:manifest-addon-loader:1.0.0-BETA+fd6decc70")

    shadow("com.dfsek.tectonic", "hocon", "4.2.1")

    shadow("com.typesafe:config:1.4.3")
}

tasks {
    processResources {
        val properties = mapOf("version" to version)
        inputs.properties(properties)
        filesMatching("terra.addon.yml") {
            expand(properties)
        }
    }
}
