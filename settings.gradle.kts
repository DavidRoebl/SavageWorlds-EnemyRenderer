pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("gson", "com.google.code.gson:gson:2.10.1")
            library("collections4", "org.apache.commons:commons-collections4:4.4")
            library("pdfbox", "org.apache.pdfbox:pdfbox:2.0.26")
            library("clikt", "com.github.ajalt.clikt:clikt:4.2.0")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "sw-enemyrenderer"
include("renderer")
include("json-helper")
include("commons")
