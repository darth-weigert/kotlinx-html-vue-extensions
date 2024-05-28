plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotlinxHtmlVersion = "0.9.1"
val kotestVersion = "5.7.2"

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig(Action {

            })
            testTask(Action{
                useKarma {

                }
            })
            webpackTask(Action {

            })
        }
//        binaries.library()
//        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-html:${kotlinxHtmlVersion}")
                implementation(npm("vue", "3.3.4"))
            }
        }
    }
}
