plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlinx.kover")
    id("maven-publish")
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotlinxHtmlVersion = "0.9.1"
val ktorVersion = "2.3.4"
val kotestVersion = "5.7.2"

val generatedCommonCodePath = layout.buildDirectory.dir("generated-sources/common/kotlin")

val generateExtCode by tasks.registering(JavaExec::class) {
    group = "codeGenerate"
    classpath = project(":gen-ext").sourceSets["main"].runtimeClasspath
    mainClass.set("dw.GenerateExtKt")
    outputs.dir(generatedCommonCodePath)
    args = listOf("--output", generatedCommonCodePath.get().asFile.toString())
}

kotlin {
    jvmToolchain(8)
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
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
                mainOutputFileName.set("mycustomfilename.js")
                output.libraryTarget = "commonjs2"

            })
        }
        binaries.library()
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
        val jsMain by getting
        val jsTest by getting
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
