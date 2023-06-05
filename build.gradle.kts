import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

repositories {
	mavenCentral()
}

allprojects {
	group = "co.bearus"
	version = "0.0.1-SNAPSHOT"

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}
}

subprojects {
	tasks.withType<Test> {
		useJUnitPlatform()
	}
//
//	val jar: Jar by tasks
//	val bootJar: BootJar by tasks
//
//	bootJar.enabled = false
//	jar.enabled = false
}
