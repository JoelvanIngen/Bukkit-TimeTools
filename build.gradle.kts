plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2" // for fat jar
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

group = "com.github.joelvaningen.timecommands"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    }
}

tasks {
    jar {
        archiveBaseName.set("TimeCommands")
        from(sourceSets.main.get().output)
    }

    shadowJar {
        archiveBaseName.set("TimeCommands")
        archiveClassifier.set("")
        from(sourceSets.main.get().output)
    }
}