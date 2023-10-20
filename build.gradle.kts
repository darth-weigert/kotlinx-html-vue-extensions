import org.gradle.api.Action

plugins {
    kotlin("multiplatform") version "1.9.0"
    jacoco
    id("maven-publish")
    id("org.ajoberstar.grgit") version "5.2.0"
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.10"
}

val kotlinxHtmlVersion = "0.9.1"
val ktorVersion = "2.3.4"
val assertjVersion = "3.24.2"
val kotestVersion = "5.7.2"

val generatedCommonCodePath = layout.buildDirectory.dir("generated-sources/common/kotlin")
//val generatedJsCodePath = layout.buildDirectory.dir("generated-sources/js/kotlin")

val generateExtCode by tasks.registering(JavaExec::class) {
    group = "codeGenerate"
    classpath = project(":gen-ext").sourceSets["main"].runtimeClasspath
    mainClass.set("dw.GenerateExtKt")
    outputs.dir(generatedCommonCodePath)
    args = listOf("--output", generatedCommonCodePath.get().asFile.toString())
}

//val generateVueBindingsCode by tasks.registering(Exec::class) {
//    group = "codeGenerate"
//    outputs.dir(generatedJsCodePath)
//
//    val tempPath = childProjects["gen-vue"]!!.layout.buildDirectory.dir("vue-conversion")
//
////    commandLine = listOf("node", layout.buildDirectory.file("js/node_modules/.bin/dukat").get().toString(),
////        "-p", "dw",
////        "-d", generatedJsCodePath.get().toString(),
////        layout.buildDirectory.file("js/node_modules/vue/dist/vue.d.ts").get().toString())
//    commandLine("node", tempPath.get().file("node_modules/.bin/dukat"),
//        "-p", "dw",
//        "-d", generatedJsCodePath.get(),
//        tempPath.get().file("node_modules/vue/dist/vue.d.ts"))
//
//    println(commandLine)
//
//    dependsOn(":gen-vue:installNpmPackages")
//}

kotlin {
    jvmToolchain(8)
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser {
            commonWebpackConfig(Action {
            })
            testTask(Action {
                useKarma {
                    useChrome()
                }
            })
            webpackTask(Action {

            })
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir(generatedCommonCodePath)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-html:${kotlinxHtmlVersion}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.kotest:kotest-assertions-core:${kotestVersion}")
            }
        }
        val ktorTest by creating {
            dependsOn(commonTest)
            kotlin.srcDir("src/ktorTest/kotlin")
            dependencies {
                implementation("io.ktor:ktor-server-tests:${ktorVersion}")
                implementation("io.ktor:ktor-server-html-builder:${ktorVersion}")
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependsOn(ktorTest)
        }
        val jsMain by getting {
//            kotlin.srcDir(generatedJsCodePath)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-html:${kotlinxHtmlVersion}")
                implementation(npm("vue", "3.3.4"))
            }
        }
        val vueTestUtils by creating {
            dependsOn(jsMain)
            kotlin.srcDir("src/vueTestUtils/kotlin")
            dependencies {
                implementation(npm("@vue/test-utils", "2.4.1"))
            }
        }
        val jsTest by getting {
            dependsOn(vueTestUtils)
        }
        val nativeMain by getting
        val nativeTest by getting {
            dependsOn(ktorTest)
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    when(name) {
        "compileCommonMainKotlinMetadata" -> dependsOn(generateExtCode)
        "compileKotlinJs" -> dependsOn(generateExtCode/*, generateVueBindingsCode*/)
        "compileKotlinJvm" -> dependsOn(generateExtCode)
        "compileKotlinNative" -> dependsOn(generateExtCode)
//        "kotlinNpmInstall" -> dependsOn(generateDukat)
    }
}

val jacocoTestReport by tasks.getting(JacocoReport::class) {
    val coverageSourceDirs = arrayOf(
        generatedCommonCodePath,
    )

    val classFiles = File("${buildDir}/classes/kotlin/jvm/")
        .walkBottomUp()
        .toSet()

    classDirectories.setFrom(classFiles)
    sourceDirectories.setFrom(files(coverageSourceDirs))

    executionData
        .setFrom(files("${buildDir}/jacoco/jvmTest.exec"))

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
