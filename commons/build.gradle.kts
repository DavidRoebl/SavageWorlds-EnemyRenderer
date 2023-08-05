plugins {
    java
    kotlin("jvm") version "1.9.0"
}

group = "dev.roebl.savageworlds"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.collections4)
}

kotlin {
    jvmToolchain(8)
}