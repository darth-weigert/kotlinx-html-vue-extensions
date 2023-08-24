plugins {
    kotlin("multiplatform") version "1.9.0"
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val generatedCodePath = layout.buildDirectory.dir("generated-sources")

tasks.register("generateCode", JavaExec::class) {
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
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
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
        val generatedMain by creating {
            kotlin.srcDirs(generatedCodePath)
        }
        val commonMain by getting {
            dependsOn(generatedMain)
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}
