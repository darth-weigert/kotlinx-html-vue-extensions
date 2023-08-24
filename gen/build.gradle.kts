plugins {
    kotlin("jvm") //version "1.9.0"
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.14.2") {
        exclude(module = "kotlin-reflect")
    }
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}


//sourceSets {
////    main {
////        java.srcDir("src/genBaseMain")
////        java {
////            setSrcDirs(listOf("genBaseMain"))
////        }
////    }
//    create("generator") {
////        kotlin.
//        java.srcDir("src/generator/java")
//    }
//}

kotlin { // Extension for easy setup
    jvmToolchain(17)

//    sourceSets {
//        val jvmMain by getting {
////            kotlin.srcDir("src/generator/kotlin")
//            dependencies {
//                implementation("com.squareup:kotlinpoet:1.14.2") {
//                    exclude(module = "kotlin-reflect")
//                }
//            }
//        }
//    }
}

//tasks.register("generateCode", JavaExec::class) {
//    group = "codeGenerate"
//    classpath = sourceSets["generator"].runtimeClasspath
//    mainClass.set("dw.GenerateExt")
//    val output = layout.buildDirectory.dir("generated-sources")
//    outputs.dir(output)
//
//    doLast {
//        val sourceDir: File = output.get().asFile
//        // ...
//    }
//    dependsOn("generatorClasses")
//}
