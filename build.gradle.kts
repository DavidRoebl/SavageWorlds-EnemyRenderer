plugins {
    kotlin("jvm") version "1.9.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
//    implementation("com.github.timrs2998:pdf-builder:1.10.0")
    implementation("org.apache.pdfbox:pdfbox:2.0.26")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.commons:commons-collections4:4.4")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}