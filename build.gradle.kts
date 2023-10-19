import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.3" apply false
    id("io.spring.dependency-management") version "1.1.3" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0" apply false
    id("com.palantir.docker") version "0.35.0" apply false

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22" apply false
    kotlin("plugin.spring") version "1.8.22" apply false
}

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    group = "hanghae99.plus2.team3"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")


    dependencies {

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        implementation("com.h2database:h2:1.4.200")
        runtimeOnly("com.mysql:mysql-connector-j")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.rest-assured:rest-assured")

        // 로그백 의존성
        implementation(group = "ca.pjer", name = "logback-awslogs-appender", version = "1.6.0")
        // 프로퍼티 제어 in xml
        implementation("org.codehaus.janino:janino:3.1.7")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs += "-Xjsr305=strict"
                jvmTarget = "17"
            }
        }
        withType<Test> {
            useJUnitPlatform()
        }
        named<Jar>("jar") {
            enabled = false
        }
    }
}

