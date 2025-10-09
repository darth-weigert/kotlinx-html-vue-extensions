plugins {
    kotlin("jvm")
}

group = "dw"
version = "1.0-SNAPSHOT"

val kotestVersion = "5.7.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.14.2") {
        exclude(module = "kotlin-reflect")
    }
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
}

kotlin {
    jvmToolchain(8)
}
