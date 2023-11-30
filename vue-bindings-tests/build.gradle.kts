plugins {
    kotlin("multiplatform")
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
//            commonWebpackConfig(Action {
//            })
            testTask(Action {
                useKarma {
                    useChrome()
                }
            })
//            webpackTask(Action {
//
//            })
        }
        binaries.library()
    }
    sourceSets {
        val jsTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-html:${kotlinxHtmlVersion}")
                implementation(project(":kotlinx-html-vue-dsl"))
                implementation(project(":vue-test-utils"))
                implementation(project(":kotlinx-html-vue-bindings"))
                implementation(kotlin("test"))
                implementation("io.kotest:kotest-assertions-core:${kotestVersion}")
//                implementation(npm("vue", "3.3.4"))
//                implementation(npm("@vue/test-utils", "2.4.1"))
            }
        }
    }
}
