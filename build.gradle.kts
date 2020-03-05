import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "com.geolocate.citizens"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.2.51")
    implementation("io.github.microutils:kotlin-logging:1.7.8")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.byteowls:jopencage:1.3.0")


    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    testImplementation("io.kotlintest:kotlintest-extensions-spring:3.4.2")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")

    testImplementation("org.junit.platform:junit-platform-commons:1.5.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")

    testImplementation("io.mockk:mockk:1.9")

    testImplementation("com.google.truth:truth:0.42")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.getByName<BootJar>("bootJar") {
    mainClassName = "com.geolocate.citizens.LocateCitizensApplication"
    manifest {
        attributes("Start-Class" to "com.geolocate.citizens.LocateCitizensApplicationKt")
    }
}

springBoot {
    mainClassName = "com.geolocate.citizens.LocateCitizensApplicationKt"
}