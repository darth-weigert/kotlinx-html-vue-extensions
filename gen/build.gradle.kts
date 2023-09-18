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

kotlin {
    jvmToolchain(8)
}
