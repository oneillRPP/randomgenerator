import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.ytree.randomgenerator"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.6")
    testImplementation(kotlin("test"))
}

val mainClassName = "io.ytree.randomgenerator.AppKt"
project.setProperty("mainClassName", mainClassName)

application {
    mainClass.set(mainClassName)
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = mainClassName
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
