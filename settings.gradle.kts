
rootProject.name = "kotlinx-html-vue-extensions"
include("gen-ext")
include("gen-vue")
include("dsl")
include("vue-binding")
include("vue-test-utils")
include("vue-bindings-tests")

project(":dsl").name = "kotlinx-html-vue-dsl"
project(":vue-binding").name = "kotlinx-html-vue-bindings"

pluginManagement {
    plugins {
        base
        kotlin("multiplatform") version "2.2.20"
        kotlin("jvm") version "2.2.20"
        id("org.jetbrains.kotlinx.kover") version "0.9.2"
        id("maven-publish")
        id("org.ajoberstar.grgit") version "5.3.3"
    }
}
