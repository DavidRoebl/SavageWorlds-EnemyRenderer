plugins {
    java
    kotlin("jvm") version "1.9.0"
    application
}

group = "dev.roebl.savageworlds"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(libs.pdfbox)
    implementation(libs.gson)
    implementation(libs.collections4)
    implementation(libs.clikt)

    implementation(project(":commons"))
}

application {
    mainClass.set("dev.roebl.savageworlds.enemyrenderer.EnemyRendererKt")
}

kotlin {
    jvmToolchain(8)
}