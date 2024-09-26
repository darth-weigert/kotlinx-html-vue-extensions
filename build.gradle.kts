
plugins {
    base
    id("org.jetbrains.kotlinx.kover")
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    kover(project(":kotlinx-html-vue-dsl"))
}
