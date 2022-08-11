plugins {
	val kotlinVersion = "1.6.21"

	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
	id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

apply{
	plugin("org.jetbrains.kotlin.plugin.jpa")
	plugin("org.jetbrains.kotlin.plugin.noarg")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.2")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.h2database:h2")
	implementation("org.springframework.boot:spring-boot-starter-data-rest:2.7.2")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2:2.1.212")
}

tasks.compileKotlin {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.test {
	useJUnitPlatform()
}
