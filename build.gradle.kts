plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io")
    maven("https://repo.maven.apache.org/maven2/")
}

dependencies {
    compileOnly("org.mariadb.jdbc:mariadb-java-client:2.6.2")
    compileOnly("org.jetbrains:annotations:19.0.0")
    implementation("com.google.inject:guice:5.0.1") {
        exclude("com.google.guava", "guava")
    }
    compileOnly("org.spigotmc:spigot-api:1.17-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}

group = "me.gtc3ch1.betterlife"
version = "1.0"
description = "BetterLife"
java.sourceCompatibility = JavaVersion.VERSION_16

tasks {
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        minimize()
        archiveFileName.set(rootProject.name + ".jar")
        exclude("META-INF/**")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
