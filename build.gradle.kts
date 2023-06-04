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
	apply(plugin = "java-library")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-spring")

	group = "co.bearus"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_17

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}
}

subprojects {
	repositories {
		mavenCentral()
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.flywaydb:flyway-core")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
		implementation("org.springframework:spring-jdbc")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.projectreactor:reactor-test")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = false
}

project(":use-case") {
	dependencies {
		implementation(project(":entity"))
	}
}

project(":infrastructure") {
	dependencies {
		implementation(project(":entity"))
		implementation(project(":use-case"))
		//api("org.springframework.boot:spring-boot-starter-data-r2dbc")
		implementation("org.springframework.boot:spring-boot-starter-data-cassandra-reactive")
		implementation("com.github.f4b6a3:ulid-creator:5.2.0")
		//DB
	}
}

project(":presenter") {
	dependencies {
		implementation(project(":entity"))
		implementation(project(":use-case"))
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("org.springframework.boot:spring-boot-starter-validation")
	}
}

project(":config") {
	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = true
	jar.enabled = true

	dependencies {
		implementation(project(":entity"))
		implementation(project(":use-case"))
		implementation(project(":presenter"))
		implementation(project(":infrastructure"))

		implementation("org.springframework.boot:spring-boot-starter-webflux")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}
}
