plugins {
    kotlin("multiplatform") version "1.9.0"
    jacoco
    id("maven-publish")
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.10"
}

val ktorVersion = "2.3.4"
val jsoupVersion = "1.16.1"
val assertjVersion = "3.24.2"

val generatedCodePath = layout.buildDirectory.dir("generated-sources")

val generateCode by tasks.registering(JavaExec::class) {
    group = "codeGenerate"
    classpath = project(":gen").sourceSets["main"].runtimeClasspath
    mainClass.set("dw.GenerateExtKt")
    outputs.dir(generatedCodePath)
    args = listOf("--output", generatedCodePath.get().asFile.toString())

    doLast {
        val sourceDir: File = generatedCodePath.get().asFile
        // ...
    }
}

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
            kotlin.srcDir(generatedCodePath)
            dependencies {
                implementation("io.ktor:ktor-server-html-builder:${ktorVersion}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-tests:${ktorVersion}")
                implementation("org.jsoup:jsoup:${jsoupVersion}")
                implementation("org.assertj:assertj-core:${assertjVersion}")
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}

tasks["compileKotlinJvm"].dependsOn(generateCode)
tasks["compileKotlinNative"].dependsOn(generateCode)

val jacocoTestReport by tasks.getting(JacocoReport::class) {
    val coverageSourceDirs = arrayOf(
        generatedCodePath,
        "src/commonMain/kotlin",
        "src/jvmMain/kotlin"
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
