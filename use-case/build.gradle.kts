plugins {
    kotlin("jvm") version "1.7.22"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":entity"))
    implementation("io.projectreactor:reactor-core:3.5.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
}
