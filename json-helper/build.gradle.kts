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
    implementation(libs.gson)
}

application {
//    mainClass.set("dev.roebl.savageworlds.enemyrenderer.EnemyRendererKt")
}

kotlin {
    jvmToolchain(8)
}