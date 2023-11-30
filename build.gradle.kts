
plugins {
    base
    id("org.jetbrains.kotlinx.kover")
}

group = "dw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotlinxHtmlVersion = "0.9.1"
val ktorVersion = "2.3.4"
val kotestVersion = "5.7.2"

//val generatedJsCodePath = layout.buildDirectory.dir("generated-sources/js/kotlin")

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

dependencies {
    kover(project(":kotlinx-html-vue-dsl"))
}

koverReport {

}
