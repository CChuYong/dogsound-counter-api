import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    id("com.google.cloud.tools.jib") version "3.3.2"
}
apply(plugin = "java-library")
apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "kotlin")
apply(plugin = "kotlin-spring")

repositories {
    mavenCentral()
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

java.sourceCompatibility = JavaVersion.VERSION_17
bootJar.enabled = true
jar.enabled = true

dependencies {
    implementation(project(":entity"))
    implementation(project(":use-case"))
    implementation(project(":presenter"))
    implementation(project(":infrastructure"))

    implementation(platform("software.amazon.awssdk:bom:2.20.56"))
    implementation("software.amazon.awssdk:s3")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework:spring-jdbc")
    implementation("com.google.firebase:firebase-admin:9.1.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    implementation("org.springframework.boot:spring-boot-starter-security")
}

val activeProfile: String? = System.getProperty("spring.profiles.active")
val repoURL: String? = System.getProperty("imageName")
val imageTag: String? = System.getProperty("imageTag")

jib {
    from {
        image = "amazoncorretto:17-alpine3.17-jdk"
    }
    to {
        image = repoURL
        tags = setOf(imageTag)
    }
    container {
        jvmFlags = listOf(
            "-Dspring.profiles.active=${activeProfile}",
            "-Dserver.port=8080",
            "-Djava.security.egd=file:/dev/./urandom",
            "-Dfile.encoding=UTF-8",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UseContainerSupport",
            "-Xms1G", //min
            "-Xmx1G", //max
            "-XX:+DisableExplicitGC", //System.gc() 방어
            "-server",
        )
        ports = listOf("8080")
    }
}
