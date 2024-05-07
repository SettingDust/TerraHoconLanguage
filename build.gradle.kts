plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.github.jmongard.git-semver-plugin") version "0.12.6"
}

group = "settingdust.terra.hoconlanguage"
version = semver.semVersion

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.dfsek.terra:api:6.4.3-BETA+ab60f14ff")
    compileOnly("com.dfsek.terra:manifest-addon-loader:1.0.0-BETA+fd6decc70")

    shadow("com.dfsek.tectonic", "hocon", "4.2.1")
}

tasks {
    processResources {
        val properties = mapOf("version" to version)
        inputs.properties(properties)
        filesMatching("terra.addon.yml") {
            expand(properties)
        }
    }

    shadowJar {
        configurations = listOf(project.configurations.shadow.get())

        archiveClassifier = ""

        dependencies {
            exclude(dependency("com.dfsek.tectonic:tectonic"))
            exclude(dependency("com.dfsek.tectonic:common"))
            exclude(dependency("commons-io:commons-io"))
        }

        mergeServiceFiles()
    }

    jar {
        finalizedBy(shadowJar)

        archiveClassifier = "dev"
    }
}
