plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser {

        }
//        binaries.library()
//        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":kotlinx-html-vue-bindings"))
                implementation(npm("@vue/test-utils", "2.4.1"))
            }
        }
    }
}
