plugins {
    java
    kotlin("jvm") version "1.9.0"
    application
}

group = "dev.roebl.savageworlds"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.apache.pdfbox:pdfbox:2.0.26")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("com.github.ajalt.clikt:clikt:4.2.0")
}

application {
    mainClass.set("dev.roebl.savageworlds.enemyrenderer.EnemyRendererKt")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}