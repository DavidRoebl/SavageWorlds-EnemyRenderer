plugins {
    java
    kotlin("jvm") version "1.9.0"
}

group = "dev.roebl.savageworlds"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.collections4)
    implementation(libs.gson)
}

kotlin {
    jvmToolchain(8)
}